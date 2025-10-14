package org.sopt.validator;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MemberValidator {

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.EMPTY_NAME.getMessage());
        }
    }

    public static LocalDate validateBirthday(String birthdayStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(birthdayStr, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_DATE_FORMAT.getMessage());
        }
    }

    public static void validateEmail(String email) {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException(ErrorCode.INVALID_EMAIL_FORMAT.getMessage());
        }
    }

    public static Gender validateGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_GENDER_TYPE.getMessage());
        }
    }
}