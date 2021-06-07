package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.CommentDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository: MongoRepository<CommentDocument, ObjectId>, CommentCustomRepository