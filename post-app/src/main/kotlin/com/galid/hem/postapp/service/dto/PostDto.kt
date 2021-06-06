package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import java.time.LocalDateTime

class PostDto {
    data class Request (
        val regionId: Long,
        val title: String,
        val contents: List<DecoratorDto>? = null,
        val mediaIds: List<MediaIdDto>? = null
    )

    data class Response(
        val postId: String,
        val userId: Long,
        val regionId: Long,
        val title: String,
        val contents: List<DecoratorDto>? = null,
        val mediaIds: List<MediaIdDto>? = null,
        val postCounter: PostCounterDto.Response? = null,
        val viewerLike: LikeDto.Response? = null,
        val visible: Boolean,
        val deletedAt: LocalDateTime? = null
    )

    data class DecoratorDto(
        val value: String,
        val type: Decorator.DecoratorType
    )

    data class MediaIdDto(
        val id: String,
        val type: MediaId.MediaType
    )
}
