package com.sw19.sofa.domain.member.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "M-001", "유효한 유저를 찾지 못했습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "M-002", "해당 이메일이 없습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
