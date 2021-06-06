package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.PostCounterDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PostCounterRepository: MongoRepository<PostCounterDocument, ObjectId>, PostCounterCustomRepository