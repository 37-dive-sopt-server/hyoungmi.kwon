package org.sopt.domain.member.validator;

import org.sopt.common.response.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MemberValidator {

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthday.getYear() + 1;

        if (age < 20) {
            throw new MemberException(ErrorCode.UNDER_AGE);
        }
    }
}