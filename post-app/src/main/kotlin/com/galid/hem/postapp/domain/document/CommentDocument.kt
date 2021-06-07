package com.galid.hem.postapp.domain.document

import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Sharded
import java.time.LocalDateTime

@Document("comment")
@Sharded(shardKey = ["post_id"])
class CommentDocument(
    @Id
    var id: ObjectId? = null,
    var postId: ObjectId? = null,
    var parentCommentId: ObjectId? = null,
    var mediaId: MediaId? = null,
    var contents: List<Decorator>,
    var likeCount: Long = 0L,
    var subCommentCount: Long = 0L,
    var actorId: Long? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDocument(createdAt, updatedAt) {
}