package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.CommentDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class CommentCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
): CommentCustomRepository {
    override fun findAllBy(
        postId: ObjectId,
        parentCommentId: ObjectId?,
        lastCommentId: ObjectId?,
        size: Int,
        sortDirection: Sort.Direction,
        sortProperty: String
    ): List<CommentDocument> {
        val criteria = Criteria("post_id").`is`(postId).and("parent_comment_id").`is`(parentCommentId)


        if (lastCommentId != null) {
            if (sortDirection == Sort.Direction.ASC) {
                criteria.and("_id").gt(lastCommentId)
            }
            else {
                criteria.and("_id").lt(lastCommentId)
            }
        }

        val query = Query(criteria)
            .with(PageRequest.of(
                0,
                size,
                Sort.by(sortDirection, sortProperty)
            ))

        return mongoTemplate.find(query, CommentDocument::class.java)
    }
}