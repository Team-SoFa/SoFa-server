package com.sw19.sofa.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.sw19.sofa.domain.auth.dto.constants.AuthConstants.EMAIL_REGEXP;


public record SignUpOrLoginReq(
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    @Schema(description = "아이디(이메일)", example = "example@example.com")
    String email,
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Schema(description = "비밀번호")
    String password)
{
}
