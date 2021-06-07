package com.galid.hem.postapp.common.extension

import com.galid.hem.postapp.domain.model.Actor

fun makeActor(viewerId: Long, comparedActorId: Long): Actor {
    return if (viewerId == comparedActorId) {
        Actor(actorId = viewerId, relationType = Actor.RelationType.ME)
    } else {
        Actor(actorId = viewerId, relationType = Actor.RelationType.OTHER)
    }
}