package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.Actor

class LikeDto {
    data class Response(
        val likeId: String,
        val postId: String,
        val actor: Actor,
    )
}