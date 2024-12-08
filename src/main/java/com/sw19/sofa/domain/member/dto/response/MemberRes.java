package com.sw19.sofa.domain.member.dto.response;

import com.sw19.sofa.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberRes(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "이름")
        String name
) {
        public MemberRes(Member member) {
                this(member.getEmail(), member.getName());
        }
}
