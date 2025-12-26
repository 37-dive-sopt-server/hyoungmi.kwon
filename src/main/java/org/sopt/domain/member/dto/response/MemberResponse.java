package org.sopt.domain.member.dto.response;

import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.entity.Member;
import java.time.LocalDate;

public record MemberResponse(
        Long memberId,
        String name,
        LocalDate birthdate,
        String email,
        Gender gender
) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getBirthdate(),
                member.getEmail(),
                member.getGender());
    }
}