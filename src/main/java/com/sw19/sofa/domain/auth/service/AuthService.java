package com.sw19.sofa.domain.auth.service;

import com.sw19.sofa.domain.auth.dto.request.SignUpOrLoginReq;
import com.sw19.sofa.domain.auth.dto.response.SignUpOrLoginRes;
import com.sw19.sofa.domain.auth.dto.response.TokenRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.entity.enums.Authority;
import com.sw19.sofa.domain.member.repository.MemberRepository;
import com.sw19.sofa.global.util.EncryptionUtil;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public SignUpOrLoginRes SignUpOrLogin(SignUpOrLoginReq req) {
        Member member = memberRepository.findByEmail(req.email()).orElse(null);
        Boolean isNew = member != null;

        if(member == null) {
            member = Member.builder()
                    .email(req.email())
                    .authority(Authority.USER)
                    .build();
            memberRepository.save(member);
        }

        String encryptId = EncryptionUtil.encrypt(member.getId());
        TokenRes tokenRes = new TokenRes(jwtTokenProvider.createAccessToken(encryptId), jwtTokenProvider.createRefreshToken(encryptId));


        return new SignUpOrLoginRes(isNew, tokenRes);
    }
}
