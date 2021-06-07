package com.galid.hem.postapp.service

import com.galid.hem.postapp.common.const.DEFAULT_FETCH_COMMENT_SIZE
import com.galid.hem.postapp.common.extension.fromDto
import com.galid.hem.postapp.common.extension.makeActor
import com.galid.hem.postapp.common.extension.toDto
import com.galid.hem.postapp.common.extension.toObjectId
import com.galid.hem.postapp.domain.document.CommentDocument
import com.galid.hem.postapp.domain.repository.CommentRepository
import com.galid.hem.postapp.domain.repository.PostCounterRepository
import com.galid.hem.postapp.domain.repository.PostRepository
import com.galid.hem.postapp.service.dto.CommentDto
import org.bson.types.ObjectId
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CommentService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val counterRepository: PostCounterRepository
) {
    fun createComment(
        postId: String,
        actorId: Long,
        request: CommentDto.Request
    ): CommentDto.Response {
        val postId = postId.toObjectId()
        postRepository.findByIdOrNull(postId)
            ?: throw RuntimeException("존재하지 않는 Post: $postId")

        counterRepository.increaseCommentCount(postId)

        val savedComment = request.parentCommentId
            ?. run { createChildComment(postId, actorId, request) }
            ?: run { createParentComment(postId, actorId, request) }

       return toResponse(savedComment, actorId)
    }

    fun getComments(
        postId: String,
        actorId: Long,
        lastCommentId: String?,
        size: Int?,
    ): List<CommentDto.Response> {
        val postId = postId.toObjectId()

        val comments = commentRepository.findAllBy(
            postId = postId,
            lastCommentId = lastCommentId?.toObjectId(),
            size = size?: DEFAULT_FETCH_COMMENT_SIZE,
            sortDirection = DEFAULT_COMMENT_SORT_DIRECTION,
            sortProperty = DEFAULT_COMMENT_SORT_PROPERTY
        )

        return comments.map { toResponse(it, actorId) }
    }

    internal fun createChildComment(
        postId: ObjectId,
        actorId: Long,
        request: CommentDto.Request
    ): CommentDocument {
        val parentComment = commentRepository.findByIdOrNull(request.parentCommentId!!.toObjectId())
            ?: throw RuntimeException("존재하지 않는 Comment: ${request.parentCommentId}")

        parentComment.subCommentCount += 1
        commentRepository.save(parentComment)

        return commentRepository.save(fromDto(postId, actorId, request))
    }

    internal fun createParentComment(
        postId: ObjectId,
        actorId: Long,
        request: CommentDto.Request
    ): CommentDocument {
        return commentRepository.save(fromDto(postId, actorId, request))
    }

    internal fun fromDto(
        postId: ObjectId,
        actorId: Long,
        request: CommentDto.Request
    ): CommentDocument {
        return CommentDocument(
            postId = postId,
            parentCommentId = request.parentCommentId?.toObjectId(),
            mediaId = request.mediaId?.fromDto(),
            contents = request.contents.map { it.fromDto() },
            actorId = actorId,
        )
    }

    internal fun toResponse(
        commentDocument: CommentDocument,
        actorId: Long
    ): CommentDto.Response {
        return CommentDto.Response(
            commentId = commentDocument.id.toString(),
            parentCommentId = commentDocument.parentCommentId?.toString(),
            postId = commentDocument.postId.toString(),
            actor = makeActor(viewerId = actorId, comparedActorId = commentDocument.actorId!!).toDto(),
            mediaId = commentDocument.mediaId?.toDto(),
            contents = commentDocument.contents.map { it.toDto() },
            subCommentCount = commentDocument.subCommentCount,
            likeCount = commentDocument.likeCount,
            createdAt = commentDocument.createdAt!!,
            updatedAt = commentDocument.updatedAt!!,
        )
    }

    companion object {
        val DEFAULT_COMMENT_SORT_DIRECTION: Sort.Direction = Sort.Direction.ASC
        const val DEFAULT_COMMENT_SORT_PROPERTY: String = "created_at"
    }
}