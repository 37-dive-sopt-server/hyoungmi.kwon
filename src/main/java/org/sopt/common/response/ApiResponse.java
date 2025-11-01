package org.sopt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

// ✨ 모든 API 응답을 일관된 JSON 구조로 반환하기 위한 DTO
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON 직렬화(객체 -> JSON) 과정에서 null을 가진 필드 제거
public record ApiResponse<T>(int code, String message, T data) {

    // ⚪ 성공 응답
    public static <T> ApiResponse<T> ok(SuccessCode code, T data) {
        return new ApiResponse<>(code.getCode(), code.getMessage(), data);
    }

    // ⚪ 실패 응답
    public static <T> ApiResponse<T> fail(ErrorCode code, T data) {
        return new ApiResponse<>(code.getCode(), code.getMessage(), data);
    }
}