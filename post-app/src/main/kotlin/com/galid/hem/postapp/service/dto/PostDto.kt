package com.galid.hem.postapp.service.dto

import java.time.LocalDateTime

class PostDto {
    data class Request (
        val regionId: Long,
        val title: String,
        val contents: List<DecoratorDto>? = null,
        val thumbnailUrl: String? = null,
        val mediaIds: List<MediaIdDto>? = null
    )

    data class Response(
        val postId: String,
        val userId: Long,
        val regionId: Long,
        val title: String,
        val contents: List<DecoratorDto>? = null,
        val thumbnailUrl: String? = null,
        val mediaIds: List<MediaIdDto>? = null,
        val postCounter: PostCounterDto.Response? = null,
        val viewerLike: LikeDto.Response? = null,
        val visible: Boolean,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val deletedAt: LocalDateTime? = null
    )
}
