package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.Actor

data class ActorDto(
    val actorId: Long,
    val profileImageUrl: String? = null,
    val relationType: Actor.RelationType
)