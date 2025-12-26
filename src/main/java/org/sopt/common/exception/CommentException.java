package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class CommentException extends GeneralException {

    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}