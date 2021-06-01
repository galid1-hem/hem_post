package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.PostDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository: MongoRepository<PostDocument, ObjectId>, PostCustomRepository