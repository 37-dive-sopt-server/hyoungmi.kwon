package org.sopt.common.response;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.sopt.common.exception.GeneralException;
import org.sopt.domain.member.entity.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

// ✨ 애플리케이션 전체에서 발생하는 예외를 catch 해서 통일된 응답(JSON)으로 반환하는 역할을 하는 클래스
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ⚪ GeneralException 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ 요청 헤더 누락 예외 처리
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingHeader(MissingRequestHeaderException e) {
        ErrorCode errorCode = ErrorCode.REQUEST_HEADER_EMPTY;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ 지원하지 않는 HTTP 메서드 예외 처리
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(Exception e) {
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ 잘못된 URL 접근 예외 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_URL;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ DTO Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.NOT_VALID_EXCEPTION;

        // e.getBindingResult().getFieldErrors(): DTO 검증 중 실패한 필드들의 정보를 모두 가져옴
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse(ErrorCode.INVALID_VALUE.getMessage()),
                        (existing, replacement) -> existing,  // 같은 필드 중복 메시지는 첫번째만
                        LinkedHashMap::new  // 순서 보장
                ));
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, errors));
    }

    // ⚪ 형식 오류 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_BODY;

        // LocalDate 형식 오류
        if (e.getCause() instanceof DateTimeParseException) {
            errorCode = ErrorCode.INVALID_DATE_FORMAT;
        }

        // Enum 변환 오류
        if (e.getCause() instanceof InvalidFormatException cause && cause.getTargetType().isEnum()) {
            // 성별 변환 오류
            if (cause.getTargetType().equals(Gender.class)) {
                errorCode = ErrorCode.INVALID_GENDER_TYPE;
            }
        }

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ Media Type 오류 예외 처리
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        ErrorCode errorCode = ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }

    // ⚪ 그 외의 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandledException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode, null));
    }
}