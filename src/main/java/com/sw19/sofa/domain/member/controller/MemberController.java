package com.sw19.sofa.domain.member.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "🔑 Member")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    @Operation(summary = "현재 로그인한 사용자 정보 조회")
    @GetMapping("/me") //자신 정보 볼때 쓸때가 있으면 사용
    public ResponseEntity<Map<String, String>> getCurrentUser(@AuthMember Member member) {
        Map<String, String> response = new HashMap<>();
        response.put("email", member.getEmail());
        response.put("name", member.getName());
        return ResponseEntity.ok(response);
    }
}