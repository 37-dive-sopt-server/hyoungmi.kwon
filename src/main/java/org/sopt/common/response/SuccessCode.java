package org.sopt.common.response;

import org.springframework.http.HttpStatus;

public enum SuccessCode {

    MEMBER_CREATED(1001, HttpStatus.CREATED, "✅ 회원이 성공적으로 등록되었습니다.")
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;

    SuccessCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
