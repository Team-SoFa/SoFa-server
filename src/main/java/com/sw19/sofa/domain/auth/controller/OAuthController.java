package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.OAuthApi;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OAuthController implements OAuthApi {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @GetMapping("/google")
    public ResponseEntity<String> getGoogleAuthUrl() {
        return ResponseEntity.ok("/oauth2/authorization/google");
    }

    @Override
    @GetMapping("/code/google")
    public ResponseEntity<OAuth2Response> googleCallback(@RequestParam("code") String code) {
        // OAuth2 인증 처리는 SecurityConfig와 OAuth2AuthenticationSuccessHandler에서 자동으로 처리됨
        return null;
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