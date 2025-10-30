package org.sopt.domain.member.repository;

import org.sopt.common.response.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.common.utils.IdGenerator;
import org.sopt.domain.member.entity.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public void deleteById(Long id) {
        if (!store.containsKey(id)) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        store.remove(id);
    }
}