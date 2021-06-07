package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.MediaId

data class MediaIdDto(
    val id: String,
    val type: MediaId.MediaType
)