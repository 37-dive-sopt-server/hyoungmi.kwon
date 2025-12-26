package org.sopt.domain.article.dto.response;

import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.entity.ArticleTag;

public record ArticleResponse(
        Long articleId,
        String memberName,
        String title,
        String content,
        ArticleTag tag
) {
    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getAuthor().getName(),
                article.getTitle(),
                article.getContent(),
                article.getTag()
        );
    }
}