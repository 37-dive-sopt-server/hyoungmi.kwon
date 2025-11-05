package org.sopt.domain.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.entity.ArticleTag;

public record ArticleCreateRequestDTO(
        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "태그는 필수입니다.")
        String tag
) {
        public Article toEntity() {
                return Article.create(title, content, ArticleTag.from(tag));
        }
}