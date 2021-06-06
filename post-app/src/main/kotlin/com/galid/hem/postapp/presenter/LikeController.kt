package com.galid.hem.postapp.presenter

import com.galid.hem.postapp.domain.document.LikeDocument
import com.galid.hem.postapp.service.LikeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts/{post_id}/likes")
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping
    fun createLike(
        @PathVariable("post_id") postId: String
    ): Response<Any> {
        likeService.createLike(
            activityId = postId,
            actorId = 1,
            activityType = LikeDocument.ActivityType.POST
        )
        return Response()
    }

    @DeleteMapping("/{like_id}")
    fun deleteLike(
        @PathVariable("post_id") postId: String,
        @PathVariable("like_id") likeId: String,
    ): Response<Any> {
        likeService.deleteLike(
            postId = postId,
            actorId = 1,
            likeId = likeId
        )
        return Response()
    }
}