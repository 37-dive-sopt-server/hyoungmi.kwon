package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class FileException extends GeneralException {

    private final ErrorCode errorCode;

    public FileException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}