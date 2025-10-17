package org.sopt.common.exception;

public enum ErrorCode {

    EMPTY_NAME("⚠️ 이름을 입력해주세요."),
    DUPLICATE_EMAIL("⚠️ 이미 존재하는 이메일입니다."),
    INVALID_DATE_FORMAT("⚠️ 올바른 날짜 형식이 아닙니다."),
    INVALID_EMAIL_FORMAT("⚠️ 올바른 이메일 형식이 아닙니다."),
    INVALID_GENDER_TYPE("⚠️ 올바른 성별을 입력해주세요."),
    MEMBER_NOT_FOUND("❌ 존재하지 않는 회원입니다."),
    UNDER_AGE("❌ 20세 미만은 가입할 수 없습니다."),

    FILE_NOT_FOUND("❌ 파일이 존재하지 않습니다."),
    FILE_LOAD_ERROR("⚠️ 파일 로드 중 에러가 발생했습니다."),
    FILE_SAVE_ERROR("⚠️ 파일 저장 중 에러가 발생했습니다."),
    FILE_FLUSH_ERROR("⚠️ 백그라운드 flush 중 에러가 발생했습니다."),
    DIRECTORY_CREATE_ERROR("⚠️ 디렉토리 생성 중 에러가 발생했습니다.")
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
