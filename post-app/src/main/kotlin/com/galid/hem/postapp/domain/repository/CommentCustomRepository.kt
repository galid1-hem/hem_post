package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.CommentDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.Sort

interface CommentCustomRepository {
    fun findAllBy(
        postId: ObjectId,
        parentCommentId: ObjectId? = null,
        lastCommentId: ObjectId? = null,
        size: Int,
        sortDirection: Sort.Direction,
        sortProperty: String
    ): List<CommentDocument>
}