package com.sw19.sofa.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberRes(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "이름")
        String name
) {
}
