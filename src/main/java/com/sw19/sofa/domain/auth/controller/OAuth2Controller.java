package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.OAuthApi;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.domain.auth.service.GoogleOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OAuth2Controller implements OAuthApi {

    private final GoogleOAuth2Service googleOAuth2Service;

    @Override
    public ResponseEntity<String> getGoogleAuthUrl() {
        return ResponseEntity.ok(googleOAuth2Service.getGoogleLoginUrl());
    }

    @Override
    public ResponseEntity<OAuth2Response> googleCallback(String code) {
        return ResponseEntity.ok(googleOAuth2Service.socialLogin(code));
    }
}