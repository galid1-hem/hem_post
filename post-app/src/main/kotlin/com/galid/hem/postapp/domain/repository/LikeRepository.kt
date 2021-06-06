package com.galid.hem.postapp.domain.repository

import com.galid.hem.postapp.domain.document.LikeDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository: MongoRepository<LikeDocument, ObjectId>, LikeCustomRepository