package com.galid.hem.postapp.presenter

import com.galid.hem.postapp.service.CommentService
import com.galid.hem.postapp.service.dto.CommentDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping
    fun createComment(
        @PathVariable("postId") postId: String,
        @RequestBody request: CommentDto.Request,
        // actorId
    ): Response<CommentDto.Response> {
        return Response(
            data = commentService.createComment(postId, 1, request)
        )
    }

    @GetMapping(value = ["", "/{commentId}"])
    fun getComments(
        @PathVariable("postId") postId: String,
        @PathVariable(value = "commentId", required = false) parentCommentId: String?,
        @RequestParam("lastCommentId") lastCommentId: String?,
        @RequestParam("size") size: Int?
        // actorId
    ): Response<List<CommentDto.Response>> {
        return Response(
            data = commentService.getComments(
                postId = postId,
                parentCommentId = parentCommentId,
                actorId = 1,
                lastCommentId = lastCommentId,
                size = size
            )
        )
    }
}