package org.sopt.service;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemoryMemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.sopt.validator.MemberValidator.*;

public class MemberServiceImpl implements MemberService {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private static long sequence = 1L;

    public Long join(String name, String birthdayStr, String email, String genderStr) {
        validateName(name);
        LocalDate birthday = validateBirthday(birthdayStr);
        validateEmail(email);
        Gender gender = validateGender(genderStr);

        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(ErrorCode.DUPLICATE_EMAIL.getMessage());
        }

        Member member = new Member(sequence++, name, birthday, email, gender);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
}
