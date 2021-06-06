package com.galid.hem.postapp.domain.repository

import org.bson.types.ObjectId

interface PostCounterCustomRepository {
    fun increaseLikeCount(postId: ObjectId)
    fun decreaseLikeCount(postId: ObjectId)
    fun increaseCommentCount(postId: ObjectId)
    fun decreaseCommentCount(postId: ObjectId)
    fun increaseBookMarkCount(postId: ObjectId)
    fun decreaseBookMarkCount(postId: ObjectId)
    fun increaseViewCount(postId: ObjectId)
    fun decreaseViewCount(postId: ObjectId)
}