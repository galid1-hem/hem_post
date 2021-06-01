package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.PostDocument
import org.bson.types.ObjectId

interface PostCustomRepository {
    fun findAllByIdIn(ids: List<ObjectId>): List<PostDocument>
    fun findAllRecently(lastPostId: ObjectId?, size: Int): List<PostDocument>
}