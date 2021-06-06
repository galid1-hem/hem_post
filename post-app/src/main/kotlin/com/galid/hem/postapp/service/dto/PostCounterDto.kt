package com.galid.hem.postapp.service.dto

class PostCounterDto {
    data class Response(
        val likeCount: Long,
        val commentCount: Long,
        val bookmarkCount: Long,
        val viewCount: Long
    )
}