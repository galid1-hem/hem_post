package com.galid.hem.postapp.presenter

import com.galid.hem.postapp.service.PostService
import com.galid.hem.postapp.service.dto.PostDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun createPost(
        @RequestBody request: PostDto.Request
    ) {
        return postService.createPost(request)
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable("postId") postId: String
    ): PostDto.Response {
        return postService.getPost(postId)
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable("postId") postId: String,
        @RequestBody request: PostDto.Request,
    ) {
        return postService.updatePost(postId, request)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable("postId") postId: String,
    ) {
        return postService.deletePost(postId)
    }
}