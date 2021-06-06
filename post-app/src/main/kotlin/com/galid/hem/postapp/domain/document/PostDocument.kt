package com.galid.hem.postapp.domain.document

import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("post")
class PostDocument(
    @Id
    var id: ObjectId? = null,
    var actorId: Long? = null,
    var regionId: Long,
    var title: String,
    var contents: List<Decorator>? = null,
    var mediaIds: List<MediaId>? = null,
    var visible: Boolean = true,
    var deletedAt: LocalDateTime? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null
): BaseDocument(createdAt, updatedAt){

    fun delete() {
        this.deletedAt = LocalDateTime.now()
        this.visible = false
    }

    fun unDelete() {
        this.deletedAt = null
        this.visible = true
    }
}