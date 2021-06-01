package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.ActorPostDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ActorPostCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ActorPostCustomRepository {
    override fun findAllByActorId(
        actorId: String,
        lastPostId: ObjectId?,
        size: Int
    ): List<ActorPostDocument> {
        val criteria = Criteria("actor_id").`is`(actorId)

        if (lastPostId != null) {
            criteria.and("id")
                .gt(lastPostId)
        }

        val query = Query(criteria)

        return mongoTemplate.find(query, ActorPostDocument::class.java)
    }
}