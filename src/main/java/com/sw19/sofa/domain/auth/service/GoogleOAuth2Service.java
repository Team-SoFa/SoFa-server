package com.sw19.sofa.domain.auth.service;

import com.sw19.sofa.domain.auth.dto.request.LoginAndSignUpReq;
import com.sw19.sofa.domain.auth.dto.response.GoogleTokenResponse;
import com.sw19.sofa.domain.auth.dto.response.GoogleUserResponse;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.domain.auth.exception.OAuth2AuthenticationProcessingException;
import com.sw19.sofa.domain.auth.exception.OAuth2ErrorCode;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.service.MemberService;
import com.sw19.sofa.domain.recycleBin.service.RecycleBinManageService;
import com.sw19.sofa.domain.setting.service.SettingService;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2Service {
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final SettingService settingService;
    private final RecycleBinManageService recycleBinManageService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.provider.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.provider.google.user-info-uri}")
    private String resourceUri;


    public String getGoogleLoginUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
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
        Member member = saveOrUpdate(userInfo.getEmail(), userInfo.getName());
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
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            log.debug("Access Token Request Params: {}", params); // 요청 파라미터 확인


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                    tokenUri,
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
                    resourceUri,
                    HttpMethod.GET,
                    entity,
                    GoogleUserResponse.class
            );
            if (response.getBody() != null) {

                return response.getBody();
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

    private Member saveOrUpdate(String email, String name) {
        Member member = memberService.getMemberByEmail(email);

        if(member == null){
            member = memberService.addMember(email, name);
            settingService.setNewUser(member);
            recycleBinManageService.addRecycleBin(member);

            log.info("Saved member name: {}", member.getName());
        }


        return member;
    }

    public OAuth2Response loginAndSignUp(LoginAndSignUpReq req) {
        Member member = saveOrUpdate(req.email(), req.name());

        String accessToken = jwtTokenProvider.createAccessToken(member.getEncryptUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEncryptUserId());

        return OAuth2Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}