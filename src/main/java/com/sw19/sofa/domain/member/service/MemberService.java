package com.sw19.sofa.domain.member.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.entity.enums.Authority;
import com.sw19.sofa.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public Member addMember(String email, String name){
        Member member = Member.builder()
                .email(email)
                .name(name)
                .authority(Authority.USER) // 기본으로는 일반유저로 권한줌
                .build();

        return memberRepository.save(member);
    }
}
