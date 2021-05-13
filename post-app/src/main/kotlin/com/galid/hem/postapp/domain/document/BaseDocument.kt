package com.galid.hem.postapp.domain.document

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

open class BaseDocument(
    @CreatedDate
    var createdAt: LocalDateTime?,
    @LastModifiedDate
    var updatedAt: LocalDateTime?) {
}
