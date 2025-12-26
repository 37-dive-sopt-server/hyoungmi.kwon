package org.sopt.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // ⚪ 아티클 생성 API
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(
            @Valid @RequestBody ArticleCreateRequest articleCreateRequestDTO
    ) {
        ArticleResponse responseDTO = articleService.createArticle(articleCreateRequestDTO);
        return ResponseEntity.status(SuccessCode.ARTICLE_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_CREATED, responseDTO));
    }

    // ⚪ 아티클 단일 조회 API
    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticle(@PathVariable Long articleId) {
        ArticleResponse responseDTO = articleService.getArticleById(articleId);
        return ResponseEntity.status(SuccessCode.ARTICLE_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_FOUND, responseDTO));
    }

    // ⚪ 아티클 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllArticles() {
        List<ArticleResponse> responseDTOs = articleService.getArticles();
        return ResponseEntity.status(SuccessCode.ARTICLE_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_FOUND, responseDTOs));
    }
}
