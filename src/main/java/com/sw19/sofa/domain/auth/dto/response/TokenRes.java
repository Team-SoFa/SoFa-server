package com.sw19.sofa.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenRes(
        @Schema(description = "access 토큰")
        String accessToken,
        @Schema(description = "refresh 토큰")
        String refreshToken
){
}
