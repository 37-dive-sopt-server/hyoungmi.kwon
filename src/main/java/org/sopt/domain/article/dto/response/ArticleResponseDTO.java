package org.sopt.domain.article.dto.response;

import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.entity.ArticleTag;

public record ArticleResponseDTO(
        Long articleId,
        String memberName,
        String title,
        String content,
        ArticleTag tag
) {
    public static ArticleResponseDTO of(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getMember().getName(),
                article.getTitle(),
                article.getContent(),
                article.getTag()
        );
    }
}