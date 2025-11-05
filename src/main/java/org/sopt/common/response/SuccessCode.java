package org.sopt.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    MEMBER_CREATED(1001, HttpStatus.CREATED, "✅ 회원이 성공적으로 등록되었습니다."),
    MEMBER_FOUND(1002, HttpStatus.OK, "✅ 회원이 성공적으로 조회되었습니다."),
    MEMBER_DELETED(1003, HttpStatus.OK, "✅ 회원이 성공적으로 삭제되었습니다.")
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;

    SuccessCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
