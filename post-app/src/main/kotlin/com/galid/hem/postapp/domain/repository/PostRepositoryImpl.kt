package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.PostDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PostRepositoryImpl(
    private val mongoTemplate: MongoTemplate
): PostCustomRepository {
    override fun findCachedByIdOrdNull(id: ObjectId): PostDocument? {
        val criteria = Criteria("id")
            .`is`(id)

        val query = Query(criteria)

        return mongoTemplate.findOne(query, PostDocument::class.java)
    }


    override fun findAllByIdIn(ids: List<ObjectId>): List<PostDocument> {
        val query = Query(Criteria("id").`in`(ids))

        return mongoTemplate.find(query, PostDocument::class.java)
    }

    override fun findAllRecently(lastPostId: ObjectId?, size: Int): List<PostDocument> {
        val criteria = Criteria()

        if (lastPostId != null) {
            criteria.and("id")
                .gt(lastPostId)
        }

        val query = Query(criteria)
            .with(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id")))

        return mongoTemplate.find(query, PostDocument::class.java)
    }
}