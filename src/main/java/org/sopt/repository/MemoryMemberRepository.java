package org.sopt.repository;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        Member newMember = new Member(++sequence, member.getName(),
                member.getBirthday(), member.getEmail(),
                member.getGender());
        store.put(newMember.getId(), newMember);
        return newMember;
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