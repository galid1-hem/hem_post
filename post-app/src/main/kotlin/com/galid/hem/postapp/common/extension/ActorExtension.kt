package com.galid.hem.postapp.common.extension

import com.galid.hem.postapp.domain.model.Actor

fun makeActor(
    viewerId: Long,
    comparedActorId: Long,
    profileImageUrl: String? = null
): Actor {
    return if (viewerId == comparedActorId) {
        Actor(actorId = viewerId, profileImageUrl=profileImageUrl, relationType = Actor.RelationType.ME)
    } else {
        Actor(actorId = viewerId, profileImageUrl=profileImageUrl, relationType = Actor.RelationType.OTHER)
    }
}