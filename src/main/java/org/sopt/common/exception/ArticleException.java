package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class ArticleException extends GeneralException {

    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }
}