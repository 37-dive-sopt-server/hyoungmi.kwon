package org.sopt.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.request.CommentCreateRequest;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.dto.response.CommentListResponse;
import org.sopt.domain.article.dto.response.CommentResponse;
import org.sopt.domain.article.service.ArticleService;
import org.sopt.domain.article.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    // ⚪ 아티클 생성 API
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(
            @Valid @RequestBody ArticleCreateRequest articleCreateRequest
    ) {
        ArticleResponse response = articleService.createArticle(articleCreateRequest);
        return ResponseEntity.status(SuccessCode.ARTICLE_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_CREATED, response));
    }

    // ⚪ 아티클 단일 조회 API
    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticle(@PathVariable Long articleId) {
        ArticleResponse response = articleService.getArticleById(articleId);
        return ResponseEntity.status(SuccessCode.ARTICLE_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_FOUND, response));
    }

    // ⚪ 아티클 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllArticles() {
        List<ArticleResponse> response = articleService.getArticles();
        return ResponseEntity.status(SuccessCode.ARTICLE_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_FOUND, response));
    }

    // ⚪ 댓글 작성 API
    @PostMapping("{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @RequestHeader("Member-Id") Long memberId,
            @PathVariable Long articleId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.createComment(memberId, articleId, request);
        return ResponseEntity
                .status(SuccessCode.COMMENT_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.COMMENT_CREATED, response));
    }

    // ⚪ 특정 아티클의 댓글 전체 조회 API
    @GetMapping("{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentListResponse>> readComments(
            @PathVariable Long articleId
    ) {
        CommentListResponse response = commentService.readComments(articleId);
        return ResponseEntity
                .status(SuccessCode.COMMENT_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.COMMENT_FOUND, response));
    }
}
