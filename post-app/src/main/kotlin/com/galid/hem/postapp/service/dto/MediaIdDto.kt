package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.MediaId
import com.galid.hem.postapp.domain.model.MediaId.MediaType.PHOTO

data class MediaIdDto(
    val id: String,
    val type: MediaId.MediaType = PHOTO,
    val imageUrl: String,
)