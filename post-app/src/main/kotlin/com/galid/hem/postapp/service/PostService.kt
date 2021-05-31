package com.galid.hem.postapp.service

import com.galid.hem.postapp.common.extension.toObjectId
import com.galid.hem.postapp.domain.document.PostDocument
import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import com.galid.hem.postapp.domain.repository.PostRepository
import com.galid.hem.postapp.service.dto.PostDto
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun createPost(
        request: PostDto.Request
    ) {
        postRepository.save(fromDto(request))
    }

    fun getPost(
        postId: String
    ): PostDto.Response {
        val foundDocument = postRepository.findById(postId.toObjectId())
            .orElseThrow { RuntimeException("Resource Not Exist!") }

        return fromDocument(foundDocument)
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
            userId = userId,
            regionId = dto.regionId,
            title = dto.title,
            contents = contents,
            mediaIds = mediaIds,
        )
    }

    internal fun fromDocument(
        postDocument: PostDocument,
        userId: Long? = null,
    ): PostDto.Response {
        val contents = postDocument.contents
            ?.map { PostDto.DecoratorDto(it.value, it.type) }

        val mediaIds = postDocument.mediaIds
            ?.map { PostDto.MediaIdDto(it.id, it.type) }

        return PostDto.Response(
            postId = postDocument.id.toString(),
            regionId = postDocument.regionId,
            userId = userId?: 1,
            title = postDocument.title,
            contents = contents,
            mediaIds = mediaIds,
            visible = postDocument.visible,
            deletedAt = postDocument.deletedAt
        )
    }
}