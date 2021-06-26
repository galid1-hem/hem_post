package com.galid.hem.postapp.service

import com.galid.hem.postapp.common.extension.makeActor
import com.galid.hem.postapp.common.extension.toObjectId
import com.galid.hem.postapp.domain.document.LikeDocument
import com.galid.hem.postapp.domain.model.Actor
import com.galid.hem.postapp.domain.repository.CommentRepository
import com.galid.hem.postapp.domain.repository.LikeRepository
import com.galid.hem.postapp.domain.repository.PostCounterRepository
import com.galid.hem.postapp.domain.repository.PostRepository
import com.galid.hem.postapp.service.dto.LikeDto
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class LikeService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
    private val postCounterRepository: PostCounterRepository
) {
    // 댓글에 좋아요도 할 수 있기 때문에 분기 처리 필요!
    fun createLike(
        activityId: String,
        activityType: LikeDocument.ActivityType,
        actorId: Long,
    ): LikeDto.Response {
        val activityId = activityId.toObjectId()
        val postDocument = postRepository.findCachedByIdOrdNull(activityId)
            ?: RuntimeException("Post($activityId)가 존재하지 않습니다.")

        return likeRepository.findByActivityIdAndActorId(activityId, actorId)
            ?.let {
                return toResponse(likeDocument = it, actorId = actorId)
            }
            ?:run {
                val createdLike = likeRepository.save(LikeDocument(actorId = actorId, activityId = activityId, type = activityType))
                postCounterRepository.increaseLikeCount(activityId)
                return toResponse(likeDocument = createdLike, actorId = actorId)
            }
    }

    // 댓글에 좋아요도 할 수 있기 때문에 분기 처리 필요!
    fun deleteLike(
        postId: String,
        actorId: Long,
        likeId: String
    ) {
        val postId = postId.toObjectId()
        val postDocument = postRepository.findCachedByIdOrdNull(postId)
            ?: RuntimeException("Post($postId)가 존재하지 않습니다.")

        likeRepository.findByPostIdAndActorId(postId, actorId)
            ?.takeIf { it.actorId == actorId }
            ?.let {
                likeRepository.deleteById(likeId.toObjectId())
                postCounterRepository.decreaseLikeCount(postId)
            }
    }

    fun getLikeForPost(
        postId: ObjectId,
        actorId: Long
    ): LikeDto.Response? {
        return likeRepository.findByPostIdAndActorId(postId, actorId)
            ?.let { toResponse(it, actorId) }
    }

    fun getLikeListForPostList(
        postIds: List<ObjectId>,
        actorId: Long
    ): List<LikeDto.Response> {
        return likeRepository.findAllByPostIdsAndActorId(postIds, actorId)
            .map { toResponse(it, actorId) }
    }

    fun toResponse(
        likeDocument: LikeDocument,
        actorId: Long
    ): LikeDto.Response{
        return LikeDto.Response(
            likeId = likeDocument.id.toString(),
            postId = likeDocument.activityId.toString(),
            actor = makeActor(actorId, likeDocument.actorId)
        )
    }
}