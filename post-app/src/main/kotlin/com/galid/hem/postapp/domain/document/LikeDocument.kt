package com.galid.hem.postapp.domain.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Sharded

@Document("like")
@Sharded(shardKey = ["activity_id"])
class LikeDocument(
    @Id
    var id: ObjectId? = null,
    var actorId: Long,
    var activityId: ObjectId,
    var type: ActivityType,
) {
    enum class ActivityType {
        POST, COMMENT
    }
}