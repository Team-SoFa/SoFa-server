package com.sw19.sofa.domain.member.api;

import com.sw19.sofa.domain.member.dto.response.MemberRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "🔑 Member")
public interface MemberApi {
    @Operation(summary = "현재 로그인한 사용자 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자 정보(이메일, 이름)")
    ResponseEntity<MemberRes> getCurrentUser(@AuthMember Member member);
}
