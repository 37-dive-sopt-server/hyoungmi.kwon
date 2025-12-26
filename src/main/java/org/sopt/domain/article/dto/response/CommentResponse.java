package org.sopt.domain.article.dto.response;

import org.sopt.domain.article.entity.Comment;
import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String content,
        Long authorId,
        String authorName,
        LocalDateTime createdAt
) {
    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getCreatedAt()
        );
    }
}