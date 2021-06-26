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
        //        clientContext: ClientContext,
        @RequestBody request: PostDto.Request
    ): Response<PostDto.Response> {
        return Response(
            data = postService.createPost(request, 1)
        )
    }

    @GetMapping
    fun getPosts(
//        clientContext: ClientContext
        @RequestParam("lastPostId") lastPostId: String?,
        @RequestParam("size") size: Int?,
    ): Response<List<PostDto.Response>> {
        return Response(
            data = postService.getPosts(
                actorId = 1,
                lastPostId = lastPostId,
                size = size,
            )
        )
    }

    @GetMapping("/actors/{actorId}")
    fun getPostsByActorId(
//        clientContext: ClientContext,
        @RequestParam("lastPostId") lastPostId: String?,
        @RequestParam("size") size: Int?,
    ): Response<List<PostDto.Response>> {
        return Response(
            data = postService.getPostsByActorId(
                actorId = 1,
                lastPostId = lastPostId,
                size = size
            )
        )
    }

    @GetMapping("/{postId}")
    fun getPost(
        //        clientContext: ClientContext,
        @PathVariable("postId") postId: String
    ): Response<PostDto.Response> {
        return Response(
            data = postService.getPost(
                postId = postId,
                actorId = 1
            )
        )
    }

    @PutMapping("/{postId}")
    fun updatePost(
        //        clientContext: ClientContext,
        @PathVariable("postId") postId: String,
        @RequestBody request: PostDto.Request,
    ): Response<Any> {
        return Response(
            data = postService.updatePost(postId, request)
        )
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        //        clientContext: ClientContext,
        @PathVariable("postId") postId: String,
    ): Response<Any> {
        return Response(
            data = postService.deletePost(postId)
        )
    }
}