package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.service.MemberService;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public Member createMember(String name, String birthdayStr, String email, String genderStr) {
        return memberService.join(name, birthdayStr, email, genderStr);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public void deleteMemberById(Long id) {
        memberService.delete(id);
    }
}
