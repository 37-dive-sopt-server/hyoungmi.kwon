package org.sopt.domain.member.controller;

import jakarta.validation.Valid;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.member.dto.request.MemberCreateRequestDTO;
import org.sopt.domain.member.dto.response.MemberResponseDTO;
import org.sopt.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponseDTO>> createMember(
            @RequestBody @Valid
            MemberCreateRequestDTO requestDTO
    ) {
        MemberResponseDTO responseDTO = memberService.join(requestDTO);
        return ResponseEntity.status(SuccessCode.MEMBER_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_CREATED, responseDTO));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> findMemberById(@PathVariable Long memberId) {
        MemberResponseDTO responseDTO = memberService.findOne(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_FOUND, responseDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponseDTO>>> getAllMembers() {
        List<MemberResponseDTO> responseDTOs = memberService.findAllMembers();
        return ResponseEntity.status(SuccessCode.MEMBER_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_FOUND, responseDTOs));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemberById(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_DELETED.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_DELETED, null));
    }
}