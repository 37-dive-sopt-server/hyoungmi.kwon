package org.sopt.domain.member.service;

import org.sopt.common.response.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberResponse;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.member.validator.MemberValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponse join(MemberCreateRequest requestDTO) {
        String name = requestDTO.name();
        LocalDate birthdate = requestDTO.birthdate();
        MemberValidator.validateAge(birthdate);
        String email = requestDTO.email();
        Gender gender = requestDTO.gender();

        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.create(name, birthdate, email, gender);
        memberRepository.save(member);
        return MemberResponse.of(member);
    }

    @Override
    public MemberResponse findOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.of(member);
    }

    @Override
    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::of)
                .toList();
    }

    @Override
    public void delete(Long memberId) {
        if (memberRepository.findById(memberId).isEmpty()) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteById(memberId);
    }
}
