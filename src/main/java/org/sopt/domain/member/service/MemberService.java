package org.sopt.domain.member.service;

import org.sopt.domain.member.dto.request.MemberCreateRequestDTO;
import org.sopt.domain.member.dto.response.MemberResponseDTO;
import java.util.List;

public interface MemberService {

    MemberResponseDTO join(MemberCreateRequestDTO requestDTO);

    MemberResponseDTO findOne(Long memberId);

    List<MemberResponseDTO> findAllMembers();

    void delete(Long memberId);
}