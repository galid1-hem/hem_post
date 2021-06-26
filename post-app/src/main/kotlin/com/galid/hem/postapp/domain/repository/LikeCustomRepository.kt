package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.LikeDocument
import org.bson.types.ObjectId

interface LikeCustomRepository {
    fun findByActivityIdAndActorId(activityId: ObjectId, actorId: Long): LikeDocument?
    fun findAllByActivityIdsAndActorId(activityIds: List<ObjectId>, actorId: Long): List<LikeDocument>
    fun findByPostIdAndActorId(postId: ObjectId, actorId: Long): LikeDocument?
    fun findAllByPostIdsAndActorId(postIds: List<ObjectId>, actorId: Long): List<LikeDocument>
}