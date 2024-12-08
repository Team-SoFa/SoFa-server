package com.sw19.sofa.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginAndSignUpReq(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "이름")
        String name
) {
}
