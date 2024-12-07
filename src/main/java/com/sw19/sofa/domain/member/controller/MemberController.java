package com.sw19.sofa.domain.member.controller;

import com.sw19.sofa.domain.member.api.MemberApi;
import com.sw19.sofa.domain.member.dto.response.MemberRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.service.MemberMangeService;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberMangeService memberMangeService;

    @GetMapping
    public ResponseEntity<MemberRes> getCurrentUser(@AuthMember Member member) {
        MemberRes res = memberMangeService.getCurrentUser(member);
        return BaseResponse.ok(res);
    }
}