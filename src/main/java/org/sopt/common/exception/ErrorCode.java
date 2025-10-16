package org.sopt.common.exception;

public enum ErrorCode {

    EMPTY_NAME("⚠️ 이름을 입력해주세요."),
    DUPLICATE_EMAIL("⚠️ 이미 존재하는 이메일입니다."),
    INVALID_DATE_FORMAT("⚠️ 올바른 날짜 형식이 아닙니다."),
    INVALID_EMAIL_FORMAT("⚠️ 올바른 이메일 형식이 아닙니다."),
    INVALID_GENDER_TYPE("⚠️ 올바른 성별을 입력해주세요."),
    MEMBER_NOT_FOUND("❌ 존재하지 않는 회원입니다."),
    UNDERAGE("❌ 20세 미만은 가입할 수 없습니다.")
    UNDER_AGE("❌ 20세 미만은 가입할 수 없습니다."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
