package org.sopt.domain.member.service;

import org.sopt.common.response.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.dto.request.MemberCreateRequestDTO;
import org.sopt.domain.member.dto.response.MemberResponseDTO;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.member.validator.MemberValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;
    public MemberServiceImpl(MemberRepository memberRepository, MemberValidator memberValidator) {
        this.memberRepository = memberRepository;
        this.memberValidator = memberValidator;
    }

    @Override
    public MemberResponseDTO join(MemberCreateRequestDTO requestDTO) {
        String name = requestDTO.name();
        LocalDate birthdate = requestDTO.birthdate();
        memberValidator.validateAge(birthdate);
        String email = requestDTO.email();
        Gender gender = requestDTO.gender();

        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.create(name, birthdate, email, gender);
        memberRepository.save(member);
        return MemberResponseDTO.of(member);
    }

    @Override
    public MemberResponseDTO findOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponseDTO.of(member);
    }

    @Override
    public List<MemberResponseDTO> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponseDTO::of)
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
