package com.galid.hem.postapp.presenter

import com.galid.hem.postapp.domain.document.LikeDocument
import com.galid.hem.postapp.service.LikeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping
    fun createLike(
        @PathVariable("postId") postId: String
    ): Response<Any> {
        return Response(
            data = likeService.createLike(
                activityId = postId,
                actorId = 1,
                activityType = LikeDocument.ActivityType.POST
            )
        )
    }

    @DeleteMapping("/{likeId}")
    fun deleteLike(
        @PathVariable("postId") postId: String,
        @PathVariable("likeId") likeId: String,
    ): Response<Any> {
        likeService.deleteLike(
            postId = postId,
            actorId = 1,
            likeId = likeId
        )
        return Response()
    }
}