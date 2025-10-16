package org.sopt.service;

import org.sopt.common.exception.ErrorCode;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.sopt.validator.MemberValidator.*;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long join(String name, String birthdayStr, String email, String genderStr) {
        validateName(name);
        LocalDate birthday = validateBirthday(birthdayStr);
        validateEmail(email);
        Gender gender = validateGender(genderStr);

        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(ErrorCode.DUPLICATE_EMAIL.getMessage());
        }

        Member member = new Member(null, name, birthday, email, gender);
        return memberRepository.save(member);

    }

    @Override
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (memberRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        memberRepository.deleteById(id);
    }
}
