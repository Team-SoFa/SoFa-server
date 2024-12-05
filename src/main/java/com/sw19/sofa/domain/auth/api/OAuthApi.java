package com.sw19.sofa.domain.auth.api;

import com.sw19.sofa.domain.auth.dto.request.LoginAndSignUpReq;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "🔐 OAuth2 인증")
public interface OAuthApi {

    @Operation(summary = "Google 로그인 URL 조회", description = "Google OAuth2 로그인을 위한 URL을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "Google 로그인 URL 반환 성공")
    ResponseEntity<String> getGoogleAuthUrl();

    @Operation(summary = "Google OAuth2 콜백 처리", description = "Google OAuth2 로그인 후 콜백을 처리합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공. JWT 토큰 반환"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<OAuth2Response> googleCallback(String code);

    @Operation(summary = "임시 로그인 및 회원가입", description = "임시 로그인 및 회원가입 API입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공. JWT 토큰 반환"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<OAuth2Response> loginAndSignUp(LoginAndSignUpReq req);

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "토큰 갱신 성공"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 리프레시 토큰",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<OAuth2Response> refreshToken(String refreshToken);
}
