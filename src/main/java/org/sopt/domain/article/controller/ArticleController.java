package org.sopt.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.article.dto.request.ArticleCreateRequestDTO;
import org.sopt.domain.article.dto.response.ArticleResponseDTO;
import org.sopt.domain.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 아티클 생성 API
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponseDTO>> createArticle(
            @Valid @RequestBody ArticleCreateRequestDTO articleCreateRequestDTO
    ) {
        ArticleResponseDTO responseDTO = articleService.createArticle(articleCreateRequestDTO);
        return ResponseEntity.status(SuccessCode.ARTICLE_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.ARTICLE_CREATED, responseDTO));
    }
}
