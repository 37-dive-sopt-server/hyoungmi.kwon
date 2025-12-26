package org.sopt.domain.member.service;

import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberResponse;
import java.util.List;

public interface MemberService {

    MemberResponse join(MemberCreateRequest request);

    MemberResponse findOne(Long memberId);

    List<MemberResponse> findAllMembers();

    void delete(Long memberId);
}