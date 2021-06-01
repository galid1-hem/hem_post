package com.galid.hem.postapp.presenter

import com.galid.hem.postapp.common.const.DEFAULT_FETCH_POST_SIZE
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

    @GetMapping
    fun getPosts(
//        clientContext: ClientContext
        @RequestParam("lastPostId") lastPostId: String?,
        @RequestParam("size") size: Int = DEFAULT_FETCH_POST_SIZE,
    ): Response<List<PostDto.Response>> {
        return Response(data = postService.getPosts(
            lastPostId = lastPostId,
            size = size
        ))
    }

    @GetMapping("/actors/{actorId}")
    fun getPostsByActorId(
//        clientContext: ClientContext,
        @RequestParam("lastPostId") lastPostId: String?,
        @RequestParam("size") size: Int = DEFAULT_FETCH_POST_SIZE,
    ): Response<List<PostDto.Response>> {
        return Response(data = postService.getPostsByActorId(
            actorId = "1",
            lastPostId = lastPostId,
            size = size
        ))
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