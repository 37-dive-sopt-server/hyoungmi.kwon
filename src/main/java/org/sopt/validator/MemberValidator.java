package org.sopt.validator;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.Gender;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MemberValidator {

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.EMPTY_NAME.getMessage());
        }
    }

    public static LocalDate validateBirthday(String birthdayStr) {
        try {
            return LocalDate.parse(birthdayStr, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new MemberException(ErrorCode.INVALID_DATE_FORMAT);
        }
    }

    public static void validateAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthday.getYear() + 1;

        if (age < 20) {
            throw new MemberException(ErrorCode.UNDER_AGE);
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