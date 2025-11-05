package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class MemberException extends GeneralException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}