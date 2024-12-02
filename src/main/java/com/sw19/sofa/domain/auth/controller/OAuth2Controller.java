package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.OAuthApi;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.domain.auth.service.CustomOAuth2UserService;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuth2Controller implements OAuthApi {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.provider.google.authorization-uri}")
    private String googleAuthUrl;

    @Override
    public ResponseEntity<String> getGoogleAuthUrl() {
        String authUrl = UriComponentsBuilder.fromUriString(googleAuthUrl)
                .queryParam("client_id", googleClientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile")
                .build().toUriString();

        return ResponseEntity.ok(authUrl);
    }

    @Override
    public ResponseEntity<OAuth2Response> googleCallback(@RequestParam("code") String code) {
        // OAuth2 인증 처리는 SecurityConfig와 OAuth2AuthenticationSuccessHandler에서 자동으로 처리됨
        return null;
    }

    @Override
    public ResponseEntity<OAuth2Response> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        //일단 Bearer 제거 되고
        refreshToken = refreshToken.substring(7);

        String encryptUserId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        jwtTokenProvider.validateRefreshToken(refreshToken, encryptUserId);

        String newAccessToken = jwtTokenProvider.createAccessToken(encryptUserId);

        return ResponseEntity.ok(OAuth2Response.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build());
    }
}