package com.sw19.sofa.domain.auth.controller;

import com.sw19.sofa.domain.auth.api.AuthApi;
import com.sw19.sofa.domain.auth.dto.request.SignUpOrLoginReq;
import com.sw19.sofa.domain.auth.dto.response.SignUpOrLoginRes;
import com.sw19.sofa.domain.auth.service.AuthService;
import com.sw19.sofa.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    @Override
    @PostMapping("/signUpOrLogin")
    public ResponseEntity<SignUpOrLoginRes> signUpOrLogin(@RequestBody @Validated SignUpOrLoginReq req) {
        SignUpOrLoginRes res = authService.SignUpOrLogin(req);
        return BaseResponse.ok(res);
    }
}
