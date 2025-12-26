package org.sopt.domain.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 300, message = "내용은 300자 이내여야 합니다.")
        String content
) {
}
