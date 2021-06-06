package com.galid.hem.postapp.domain.document

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Sharded

@Document("post_counter")
@Sharded(shardKey = ["post_id"])
class PostCounterDocument(
    var postId: ObjectId? = null,
    var likeCount: Long,
    var commentCount: Long,
    var viewCount: Long,
    var bookmarkCount: Long,
): BaseDocument()