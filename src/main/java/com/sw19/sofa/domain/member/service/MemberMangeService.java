package com.sw19.sofa.domain.member.service;

import com.sw19.sofa.domain.member.dto.response.MemberRes;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMangeService {
    private final MemberService memberService;

    public MemberRes getCurrentUser(Member member) {
        return new MemberRes(member.getEmail(), member.getName());
    }
}
