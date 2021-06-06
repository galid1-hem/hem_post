package com.galid.hem.postapp.service

import com.galid.hem.postapp.common.const.DEFAULT_FETCH_POST_SIZE
import com.galid.hem.postapp.common.extension.toObjectId
import com.galid.hem.postapp.domain.document.ActorPostDocument
import com.galid.hem.postapp.domain.document.LikeDocument
import com.galid.hem.postapp.domain.document.PostCounterDocument
import com.galid.hem.postapp.domain.document.PostDocument
import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import com.galid.hem.postapp.domain.repository.ActorPostRepository
import com.galid.hem.postapp.domain.repository.LikeRepository
import com.galid.hem.postapp.domain.repository.PostCounterRepository
import com.galid.hem.postapp.domain.repository.PostRepository
import com.galid.hem.postapp.service.dto.LikeDto
import com.galid.hem.postapp.service.dto.PostCounterDto
import com.galid.hem.postapp.service.dto.PostDto
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val actorPostRepository: ActorPostRepository,
    private val likeService: LikeService,
    private val postCounterRepository: PostCounterRepository,
) {
    fun createPost(
        request: PostDto.Request,
        actorId: Long
    ) {
        val savedPostDocument = postRepository.save(fromDto(request))

        actorPostRepository.save(
            ActorPostDocument(savedPostDocument.id!!, actorId)
        )

        postCounterRepository.save(
            PostCounterDocument(savedPostDocument.id, 0, 0, 0, 0)
        )
    }

    // 성능상 이슈가 있을것 같음 고민해 봐야 함
    // : PostId가 샤드키 이기 때문에 최근 것을 가져오기 위해서 모든 샤딩된 디비를 조회할 수도 있음
    fun getPosts(
        actorId: Long,
        lastPostId: String?,
        size: Int?
    ): List<PostDto.Response> {
        val lastPostId = lastPostId?.let { ObjectId(it) }

        val foundPostList = postRepository.findAllRecently(
            lastPostId = lastPostId,
            size = size ?: DEFAULT_FETCH_POST_SIZE
        )

        return aggregatePostListWithOtherResponse(foundPostList, actorId)
    }

    fun getPostsByActorId(
        actorId: Long,
        lastPostId: String?,
        size: Int?
    ): List<PostDto.Response> {
        // actorId로 postId들을 받아온다
        val lastPostId = lastPostId?.let { ObjectId(it) }
        val postIds = actorPostRepository.findAllByActorId(
            actorId = actorId,
            lastPostId = lastPostId,
            size = size ?: DEFAULT_FETCH_POST_SIZE
        )
            .map { it.postId }

        return aggregatePostListWithOtherResponse(
            posts = postRepository.findAllByIdIn(postIds),
            actorId = actorId
        )
    }

    fun getPost(
        postId: String,
        actorId: Long,
    ): PostDto.Response {
        val foundPostDocument = postRepository.findById(postId.toObjectId())
            .orElseThrow { RuntimeException("Resource Not Exist!") }

        return aggregatePostWithOtherResponse(foundPostDocument, actorId)
    }

    fun updatePost(
        postId: String,
        request: PostDto.Request
    ) {
        val existDocument = postRepository.findById(postId.toObjectId())
            .orElseThrow { RuntimeException("Resource Not Exist!") }

        val willDocument = fromDto(dto = request)
        existDocument.mediaIds = willDocument.mediaIds
        existDocument.contents = willDocument.contents

        postRepository.save(existDocument)
    }

    fun deletePost(
        postId: String
    ) {
        val foundPost = postRepository.findById(postId.toObjectId())
            .orElseThrow { RuntimeException("Resource Not Exist!") }

        foundPost.delete()

        postRepository.save(foundPost)
    }

    internal fun fromDto(
        dto: PostDto.Request,
        userId: Long? = null,
    ): PostDocument {
        val contents = dto.contents
            ?.map { Decorator(it.value, it.type) }
        val mediaIds = dto.mediaIds
            ?.map { MediaId(it.id, it.type) }

        return PostDocument(
            actorId = userId,
            regionId = dto.regionId,
            title = dto.title,
            contents = contents,
            mediaIds = mediaIds,
        )
    }

    internal fun aggregatePostListWithOtherResponse(
        posts: List<PostDocument>,
        actorId: Long
    ): List<PostDto.Response> {
        val postIdList = posts.mapNotNull { it.id }
            .toSet()
            .toList()

        val postCounterMap = postCounterRepository.findAllById(postIdList)
            .map {
                it.postId to
                    PostCounterDto.Response(
                        likeCount = it.likeCount,
                        commentCount = it.commentCount,
                        bookmarkCount = it.bookmarkCount,
                        viewCount = it.viewCount
                    )
            }.toMap()

        val viewerLikeMap = likeService.getLikeListForPostList(postIdList, actorId)
            .map { it.postId to it }
            .toMap()

        return posts.map {
            toResponse(
                postDocument = it,
                actorId = actorId,
                postCounter = postCounterMap[it.id],
                viewerLike = viewerLikeMap[it.id]
            )
        }
    }

    internal fun aggregatePostWithOtherResponse(
        post: PostDocument,
        actorId: Long
    ): PostDto.Response {
        val postCounter = postCounterRepository.findByIdOrNull(post.id!!)
            ?.let {
                PostCounterDto.Response(
                    likeCount = it.likeCount,
                    commentCount = it.commentCount,
                    bookmarkCount = it.bookmarkCount,
                    viewCount = it.viewCount
                )
            }

        val viewerLike = likeService.getLikeForPost(post.id!!, actorId)

        return toResponse(
            postDocument = post,
            actorId = actorId,
            postCounter = postCounter,
            viewerLike = viewerLike
        )
    }

    internal fun toResponse(
        postDocument: PostDocument,
        actorId: Long,
        postCounter: PostCounterDto.Response?,
        viewerLike: LikeDto.Response?,
    ): PostDto.Response {
        val contents = postDocument.contents
            ?.map { PostDto.DecoratorDto(it.value, it.type) }

        val mediaIds = postDocument.mediaIds
            ?.map { PostDto.MediaIdDto(it.id, it.type) }

        return PostDto.Response(
            postId = postDocument.id.toString(),
            regionId = postDocument.regionId,
            userId = actorId,
            title = postDocument.title,
            contents = contents,
            mediaIds = mediaIds,
            visible = postDocument.visible,
            deletedAt = postDocument.deletedAt,
            postCounter = postCounter,
            viewerLike = viewerLike
        )
    }
}