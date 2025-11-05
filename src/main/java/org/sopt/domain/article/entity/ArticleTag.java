package org.sopt.domain.article.entity;

import org.sopt.common.exception.ArticleException;
import org.sopt.common.response.ErrorCode;

import java.util.Arrays;

public enum ArticleTag {

    CS, DB, SPRING, ETC;

    public static ArticleTag from(String value) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ArticleException(ErrorCode.INVALID_TAG));
    }
}
