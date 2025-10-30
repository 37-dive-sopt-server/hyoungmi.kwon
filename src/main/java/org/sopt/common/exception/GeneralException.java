package org.sopt.common.exception;

import org.sopt.common.response.ErrorCode;

public class GeneralException extends RuntimeException {

  private final ErrorCode errorCode;

  public GeneralException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}