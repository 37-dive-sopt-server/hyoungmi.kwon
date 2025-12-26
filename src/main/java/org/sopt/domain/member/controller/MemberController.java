package org.sopt.domain.member.controller;

import jakarta.validation.Valid;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.response.SuccessCode;
import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberResponse;
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
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @RequestBody @Valid MemberCreateRequest request
    ) {
        MemberResponse response = memberService.join(request);
        return ResponseEntity.status(SuccessCode.MEMBER_CREATED.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_CREATED, response));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> findMemberById(@PathVariable Long memberId) {
        MemberResponse response = memberService.findOne(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_FOUND, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> response = memberService.findAllMembers();
        return ResponseEntity.status(SuccessCode.MEMBER_FOUND.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_FOUND, response));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemberById(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_DELETED.getStatus())
                .body(ApiResponse.ok(SuccessCode.MEMBER_DELETED, null));
    }
}