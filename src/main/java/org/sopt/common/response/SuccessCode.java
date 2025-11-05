package org.sopt.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // Member
    MEMBER_CREATED(2001, HttpStatus.CREATED, "✅ 회원이 성공적으로 등록되었습니다."),
    MEMBER_FOUND(2002, HttpStatus.OK, "✅ 회원이 성공적으로 조회되었습니다."),
    MEMBER_DELETED(2003, HttpStatus.OK, "✅ 회원이 성공적으로 삭제되었습니다."),

    // Article
    ARTICLE_CREATED(3001, HttpStatus.CREATED, "✅ 아티클이 성공적으로 등록되었습니다."),
    ARTICLE_FOUND(3002, HttpStatus.OK, "✅ 아티클이 성공적으로 조회되었습니다.")
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;
}
