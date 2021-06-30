package com.galid.hem.postapp.domain.model

class MediaId(
    val id: String,
    val type: MediaType,
    val imageUrl: String,
) {
    enum class MediaType {
        PHOTO, VIDEO
    }
}