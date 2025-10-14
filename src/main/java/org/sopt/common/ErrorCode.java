package org.sopt.common;

public enum ErrorCode {

    EMPTY_NAME("⚠️ 이름을 입력해주세요."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
