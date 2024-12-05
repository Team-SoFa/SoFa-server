package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.OAuthApi;
import com.sw19.sofa.domain.auth.dto.request.LoginAndSignUpReq;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.domain.auth.service.GoogleOAuth2Service;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class OAuth2Controller implements OAuthApi {

    private final GoogleOAuth2Service googleOAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<String> getGoogleAuthUrl() {
        return ResponseEntity.ok(googleOAuth2Service.getGoogleLoginUrl());
    }

    @Override
    public ResponseEntity<OAuth2Response> googleCallback(String code) {
        return ResponseEntity.ok(googleOAuth2Service.socialLogin(code));
    }

    @Override
    @PostMapping("/signUpOrLogin")
    public ResponseEntity<OAuth2Response> loginAndSignUp(@RequestBody LoginAndSignUpReq req) {
        OAuth2Response res = googleOAuth2Service.loginAndSignUp(req);
        return ResponseEntity.ok(res);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<OAuth2Response> refreshToken(@RequestHeader("Authorization") String refreshToken) {
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