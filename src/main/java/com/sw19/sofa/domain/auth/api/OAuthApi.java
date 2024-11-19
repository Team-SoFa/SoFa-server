package com.sw19.sofa.domain.auth.api;

import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "🔐 OAuth2")
@RequestMapping("/login/oauth2")
public interface OAuthApi {

    @Operation(summary = "Google OAuth2 로그인", description = "Google OAuth2 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공 시 JWT 토큰 반환"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "code: O-001 | message: OAuth2 인증 처리 중 오류가 발생했습니다. <br>" +
                            "code: O-002 | message: OAuth2 액세스 토큰을 가져오는데 실패했습니다. <br>" +
                            "code: O-003 | message: OAuth2 사용자 정보를 가져오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/code/google")
    ResponseEntity<OAuth2Response> googleCallback(@RequestParam("code") String code);

    @Operation(summary = "Google OAuth2 로그인 URL 조회", description = "Google OAuth2 로그인 URL을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "Google OAuth2 로그인 URL")
    @GetMapping("/google")
    ResponseEntity<String> getGoogleAuthUrl();
}