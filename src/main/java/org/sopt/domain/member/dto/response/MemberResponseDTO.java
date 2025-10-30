package org.sopt.domain.member.dto.response;

import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.entity.Member;
import java.time.LocalDate;

public record MemberResponseDTO(
        Long memberId,
        String name,
        LocalDate birthdate,
        String email,
        Gender gender
) {
    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getName(),
                member.getBirthdate(),
                member.getEmail(),
                member.getGender());
    }
}