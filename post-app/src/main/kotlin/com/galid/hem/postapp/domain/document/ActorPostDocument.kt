package com.galid.hem.postapp.domain.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Sharded
import java.time.LocalDateTime

@Document("actor_post")
@Sharded(shardKey = ["actor_id"])
class ActorPostDocument(
    @Id
    var postId: ObjectId,
    var actorId: Long
)