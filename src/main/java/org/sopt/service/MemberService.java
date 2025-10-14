package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private MemoryMemberRepository memberRepository;
    private static long sequence = 1L;

    public Long join(String name) {

        Member member = new Member(sequence++, name);
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