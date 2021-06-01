package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.ActorPostDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ActorPostRepository: MongoRepository<ActorPostDocument, ObjectId>, ActorPostCustomRepository {
}