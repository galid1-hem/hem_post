package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.ActorPostDocument
import org.bson.types.ObjectId

interface ActorPostCustomRepository {
    fun findAllByActorId(actorId: String, lastPostId: ObjectId?, size: Int): List<ActorPostDocument>
}