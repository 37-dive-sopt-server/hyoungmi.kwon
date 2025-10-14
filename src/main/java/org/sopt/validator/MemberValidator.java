package org.sopt.validator;

import org.sopt.common.ErrorCode;

public class MemberValidator {

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.EMPTY_NAME.getMessage());
        }
    }
}