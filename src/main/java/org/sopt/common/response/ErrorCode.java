package org.sopt.common.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // common
    REQUEST_HEADER_EMPTY(1001, HttpStatus.BAD_REQUEST, "요청 헤더가 비어있습니다."),
    NOT_VALID_EXCEPTION(1002, HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND_URL(1003, HttpStatus.NOT_FOUND, "존재하지 않는 URL 입니다."),
    METHOD_NOT_ALLOWED(1004, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(1005, HttpStatus.NOT_ACCEPTABLE, "요청한 미디어 타입을 제공할 수 없습니다."),
    INVALID_REQUEST_BODY(1006, HttpStatus.BAD_REQUEST, "⚠️ 요청 본문이 올바르지 않습니다."),
    INVALID_VALUE(1007, HttpStatus.BAD_REQUEST, "⚠️ 값이 비어있거나 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR(1008, HttpStatus.INTERNAL_SERVER_ERROR,  "서버 내부 오류가 발생했습니다."),

    // Member
    EMPTY_NAME(2001, HttpStatus.BAD_REQUEST, "⚠️ 이름을 입력해주세요."),
    DUPLICATE_EMAIL(2002, HttpStatus.BAD_REQUEST,"⚠️ 이미 존재하는 이메일입니다."),
    INVALID_DATE_FORMAT(2003, HttpStatus.BAD_REQUEST,"⚠️ 올바른 날짜 형식이 아닙니다."),
    INVALID_EMAIL_FORMAT(2004, HttpStatus.BAD_REQUEST,"⚠️ 올바른 이메일 형식이 아닙니다."),
    INVALID_GENDER_TYPE(2005, HttpStatus.BAD_REQUEST,"⚠️ 올바른 성별을 입력해주세요. (MALE | FEMALE)"),
    UNDER_AGE(2006, HttpStatus.BAD_REQUEST, "❌ 20세 미만은 가입할 수 없습니다."),
    MEMBER_NOT_FOUND(2007, HttpStatus.NOT_FOUND, "❌ 존재하지 않는 회원입니다."),

    // File
    FILE_NOT_FOUND( 3001, HttpStatus.NOT_FOUND, "❌ 파일이 존재하지 않습니다."),
    FILE_LOAD_ERROR(3002, HttpStatus.INTERNAL_SERVER_ERROR,"⚠️ 파일 로드 중 에러가 발생했습니다."),
    FILE_SAVE_ERROR(3003, HttpStatus.INTERNAL_SERVER_ERROR,"⚠️ 파일 저장 중 에러가 발생했습니다."),
    FILE_FLUSH_ERROR(3004, HttpStatus.INTERNAL_SERVER_ERROR,"⚠️ 백그라운드 flush 중 에러가 발생했습니다."),
    DIRECTORY_CREATE_ERROR(3005, HttpStatus.INTERNAL_SERVER_ERROR,"⚠️ 디렉토리 생성 중 에러가 발생했습니다."),
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
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