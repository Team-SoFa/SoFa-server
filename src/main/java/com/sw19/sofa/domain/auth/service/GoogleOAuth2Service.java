package com.sw19.sofa.domain.auth.service;

import com.sw19.sofa.domain.auth.config.GoogleOAuth2Config;
import com.sw19.sofa.domain.auth.dto.response.GoogleTokenResponse;
import com.sw19.sofa.domain.auth.dto.response.GoogleUserResponse;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.domain.auth.exception.OAuth2AuthenticationProcessingException;
import com.sw19.sofa.domain.auth.exception.OAuth2ErrorCode;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.entity.enums.Authority;
import com.sw19.sofa.domain.member.repository.MemberRepository;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2Service {

    private final GoogleOAuth2Config config;
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    public String getGoogleLoginUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + config.getClientId() +
                "&redirect_uri=" + config.getRedirectUri() +
                "&response_type=code" +
                "&scope=email profile";
    }


    public OAuth2Response socialLogin(String code) {
        // 1. 구글 OAuth2 Access Token 받기
        String googleAccessToken = getGoogleAccessToken(code);
        System.out.println("Access Token: " + googleAccessToken);

        // 2. 구글 사용자 정보 받기
        GoogleUserResponse userInfo = getGoogleUserInfo(googleAccessToken);
        System.out.println("User Info from Google - email: " + userInfo.getEmail() + ", name: " + userInfo.getName());

        // 3. 사용자 정보로 회원가입 또는 로그인 처리
        Member member = saveOrUpdate(userInfo);
        System.out.println("Saved Member Info - email: " + member.getEmail() + ", name: " + member.getName());

        // 4. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getEncryptUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEncryptUserId());

        return OAuth2Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    private String getGoogleAccessToken(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", config.getClientId());
            params.add("client_secret", config.getClientSecret());
            params.add("redirect_uri", config.getRedirectUri());
            params.add("code", code);

            log.debug("Access Token Request Params: {}", params); // 요청 파라미터 확인


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                    config.getTokenUri(),
                    request,
                    GoogleTokenResponse.class
            );

            log.debug("Access Token Response: {}", response);

            if (response.getBody() != null) {
                return response.getBody().getAccessToken();
            }
            throw new OAuth2AuthenticationProcessingException(
                    "액세스 토큰을 가져오는데 실패했습니다.",
                    OAuth2ErrorCode.ACCESS_TOKEN_ERROR
            );
        } catch (RestClientException e) {
            log.error("Failed to get Access Token: {}", e.getMessage());
            throw new OAuth2AuthenticationProcessingException(
                    e.getMessage(),
                    OAuth2ErrorCode.ACCESS_TOKEN_ERROR
            );
        }
    }

    private GoogleUserResponse getGoogleUserInfo(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<GoogleUserResponse> response = restTemplate.exchange(
                    config.getResourceUri(),
                    HttpMethod.GET,
                    entity,
                    GoogleUserResponse.class
            );
            if (response.getBody() != null) {

                GoogleUserResponse userInfo = response.getBody();
                return userInfo;
                // return response.getBody();
            }
            throw new OAuth2AuthenticationProcessingException(
                    "사용자 정보를 가져오는데 실패했습니다.",
                    OAuth2ErrorCode.USER_INFO_RESPONSE_ERROR
            );
        } catch (RestClientException e) {
            log.error("Failed to get User Info: {}", e.getMessage());
            throw new OAuth2AuthenticationProcessingException(
                    e.getMessage(),
                    OAuth2ErrorCode.USER_INFO_RESPONSE_ERROR
            );
        }
    }

    private Member saveOrUpdate(GoogleUserResponse userInfo) {

        Member member = memberRepository.findByEmail(userInfo.getEmail())
                .orElse(Member.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .authority(Authority.USER)
                        .build());

        Member savedMember = memberRepository.save(member);
        log.info("Saved member name: {}", savedMember.getName());

        return memberRepository.save(member);
    }
}