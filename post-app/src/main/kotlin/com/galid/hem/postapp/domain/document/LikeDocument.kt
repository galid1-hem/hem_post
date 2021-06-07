package com.galid.hem.postapp.domain.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Sharded
import java.time.LocalDateTime

@Document("like")
@Sharded(shardKey = ["activity_id"])
class LikeDocument(
    @Id
    var id: ObjectId? = null,
    var actorId: Long,
    var activityId: ObjectId,
    var type: ActivityType,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDocument(createdAt, updatedAt) {
    enum class ActivityType {
        POST, COMMENT
    }
}