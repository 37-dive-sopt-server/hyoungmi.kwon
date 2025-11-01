package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class MemberException extends GeneralException {

    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}