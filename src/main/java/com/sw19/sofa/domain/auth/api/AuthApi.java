package com.sw19.sofa.domain.auth.api;

import com.sw19.sofa.domain.auth.dto.request.SignUpOrLoginReq;
import com.sw19.sofa.domain.auth.dto.response.SignUpOrLoginRes;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "\uD83D\uDD10 Auth")
public interface AuthApi {
    @Operation(summary = "로그인", description = "로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access 토큰, refresh 토큰 및 신규 회원 여부(= false) "),
            @ApiResponse(responseCode = "404", description = "code: A-001 | message: 존재하지 않는 아이디입니다. <br>" +
                    "code: A-002 | message: 비밀번호가 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SignUpOrLoginRes> signUpOrLogin(SignUpOrLoginReq req);
}
