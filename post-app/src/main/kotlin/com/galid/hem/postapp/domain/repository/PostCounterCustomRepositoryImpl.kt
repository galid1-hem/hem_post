package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.PostCounterDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

class PostCounterCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
): PostCounterCustomRepository {
    override fun increaseLikeCount(postId: ObjectId) {
        doInc(postId, "like_count", 1)
    }

    override fun decreaseLikeCount(postId: ObjectId) {
        doInc(postId, "like_count", -1)
    }

    override fun increaseCommentCount(postId: ObjectId) {
        doInc(postId, "comment_count", 1)
    }

    override fun decreaseCommentCount(postId: ObjectId) {
        doInc(postId, "comment_count", -1)
    }

    override fun increaseBookMarkCount(postId: ObjectId) {
        doInc(postId, "bookmark_count", 1)

    }

    override fun decreaseBookMarkCount(postId: ObjectId) {
        doInc(postId, "bookmark_count", -1)
    }

    override fun increaseViewCount(postId: ObjectId) {
        doInc(postId, "view_count", 1)
    }

    override fun decreaseViewCount(postId: ObjectId) {
        doInc(postId, "view_count", -1)
    }

    private fun doInc(postId: ObjectId, targetToInc: String, numberToInc: Int) {
        val query = Query(Criteria("_id").`is`(postId))

        val update = Update()
            .inc(targetToInc, numberToInc)

        mongoTemplate.updateFirst(query, update, PostCounterDocument::class.java)
    }
}