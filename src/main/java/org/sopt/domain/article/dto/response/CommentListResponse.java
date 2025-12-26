package org.sopt.domain.article.dto.response;

import org.sopt.domain.article.entity.Comment;
import java.util.List;

public record CommentListResponse(
        List<CommentResponse> comments
) {
    public static CommentListResponse of(List<Comment> comments) {
        return new CommentListResponse(
                comments.stream()
                        .map(CommentResponse::of)
                        .toList()
        );
    }

}