package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.LikeDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class LikeCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
): LikeCustomRepository {
    override fun findByPostIdAndActorId(postId: ObjectId, actorId: Long): LikeDocument? {
        val criteria = Criteria("activity_id").`is`(postId)
            .and("actor_id").`is`(actorId)

        val query = Query(criteria)

        return mongoTemplate
            .findOne(query, LikeDocument::class.java)
    }

    override fun findAllByPostIdsAndActorId(postIds: List<ObjectId>, actorId: Long): List<LikeDocument> {
        val criteria = Criteria("activity_id").`in`(postIds)
            .and("actor_id").`is`(actorId)

        val query = Query(criteria)

        return mongoTemplate
            .find(query, LikeDocument::class.java)
    }
}