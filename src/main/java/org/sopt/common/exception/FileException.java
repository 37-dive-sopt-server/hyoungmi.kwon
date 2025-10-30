package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class FileException extends RuntimeException {

    private final ErrorCode errorCode;

    public FileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}