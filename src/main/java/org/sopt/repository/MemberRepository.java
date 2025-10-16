package org.sopt.repository;

import org.sopt.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
