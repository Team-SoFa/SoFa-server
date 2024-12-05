package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.OAuthApi;
import com.sw19.sofa.domain.auth.dto.request.LoginAndSignUpReq;
import com.sw19.sofa.domain.auth.dto.response.TokenRes;
import com.sw19.sofa.domain.auth.service.GoogleOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class OAuth2Controller implements OAuthApi {

    private final GoogleOAuth2Service googleOAuth2Service;

    @Override
    @GetMapping("/google")
    public ResponseEntity<String> getGoogleAuthUrl() {
        String res = googleOAuth2Service.getGoogleLoginUrl();
        return ResponseEntity.ok(res);
    }

    @Override
    @GetMapping("/code/google")
    public ResponseEntity<TokenRes> googleCallback(@RequestParam String code) {
        TokenRes res = googleOAuth2Service.socialLogin(code);
        return ResponseEntity.ok(res);
    }

    @Override
    @PostMapping("/signUpOrLogin")
    public ResponseEntity<TokenRes> loginAndSignUp(@RequestBody LoginAndSignUpReq req) {
        TokenRes res = googleOAuth2Service.loginAndSignUp(req);
        return ResponseEntity.ok(res);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<TokenRes> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        TokenRes res = googleOAuth2Service.refreshToken(refreshToken);
        return ResponseEntity.ok(res);
    }
}