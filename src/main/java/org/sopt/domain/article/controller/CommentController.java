package org.sopt.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.article.dto.request.CommentUpdateRequest;
import org.sopt.domain.article.dto.response.CommentResponse;
import org.sopt.domain.article.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // ⚪ 댓글 수정 API
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @RequestHeader("Member-Id") Long memberId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse response = commentService.updateComment(memberId, commentId, request);
        return ResponseEntity
                .status(SuccessCode.COMMENT_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.COMMENT_CREATED, response));
    }

    // ⚪ 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader("Member-Id") Long memberId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(memberId, commentId);
        return ResponseEntity
                .status(SuccessCode.COMMENT_DELETED.getStatus())
                .body(ApiResponse.ok(SuccessCode.COMMENT_DELETED, null));
    }
}
