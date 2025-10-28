package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @PostMapping("/users")
    public Member createMember(String name, String birthdayStr, String email, String genderStr) {
        return memberService.join(name, birthdayStr, email, genderStr);
    }

    @GetMapping("/users")
    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    @DeleteMapping("/users")
    public void deleteMemberById(Long id) {
        memberService.delete(id);
    }
}
