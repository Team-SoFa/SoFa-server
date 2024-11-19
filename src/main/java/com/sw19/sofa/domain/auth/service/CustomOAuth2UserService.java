package com.sw19.sofa.domain.auth.service;

import com.sw19.sofa.domain.auth.dto.CustomOAuth2User;
import com.sw19.sofa.domain.auth.exception.OAuth2AuthenticationProcessingException;
import com.sw19.sofa.domain.auth.exception.OAuth2ErrorCode;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.entity.enums.Authority;
import com.sw19.sofa.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oauth2User);
        } catch (Exception e) {
            throw new OAuth2AuthenticationProcessingException(OAuth2ErrorCode.AUTHENTICATION_PROCESSING_ERROR);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        GoogleUserInfo userInfo = new GoogleUserInfo(oauth2User.getAttributes());

        Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());
        Member member;

        if(memberOptional.isPresent()) {
            member = memberOptional.get();
            if(!userInfo.getEmail().equals(member.getEmail())) {
                member = updateExistingUser(member, userInfo);
            }
        } else {
            member = registerNewUser(userInfo);
        }

        return new CustomOAuth2User(member, oauth2User.getAttributes(), "sub");
    }

    private Member registerNewUser(GoogleUserInfo userInfo) {
        Member member = Member.builder()
                .email(userInfo.getEmail())
                .authority(Authority.USER)
                .build();

        return memberRepository.save(member);
    }

    private Member updateExistingUser(Member member, GoogleUserInfo userInfo) {

        return memberRepository.save(member);
    }

    // Google User Info를 담는 내부 클래스
    private static class GoogleUserInfo {
        private Map<String, Object> attributes;

        public GoogleUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public String getEmail() {
            return (String) attributes.get("email");
        }

        public String getName() {
            return (String) attributes.get("name");
        }
    }
}
