package org.sopt.domain.member.service;

import org.sopt.domain.Member;
import org.sopt.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member join(String name, String email, String password, String role);

    Optional<Member> findOne(Long memberId);

    List<Member> findAllMembers();

    void delete(Long id);
}