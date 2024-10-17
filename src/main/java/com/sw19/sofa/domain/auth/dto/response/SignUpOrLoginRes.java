package com.sw19.sofa.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpOrLoginRes(
        @Schema(description = "신규 사용자 여부 - 해당 경우 사용자 설정 과정 수행")
        Boolean isNew,

        @Schema(description = "access 토큰, refresh 토큰")
        TokenRes token
) {
}
