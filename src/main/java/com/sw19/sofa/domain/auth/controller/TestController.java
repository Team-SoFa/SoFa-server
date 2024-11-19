package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/member")
    public ResponseEntity<Map<String, String>> getUserInfo(@AuthMember Member member) {
        Map<String, String> response = new HashMap<>();
        response.put("email", member.getEmail());
        response.put("message", "인증된 사용자입니다.");
        return ResponseEntity.ok(response);
    }
}