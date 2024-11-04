package com.sw19.sofa.domain.auth.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "A-001", "존재하지 않는 이메일입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "A-002", "잘못된 비밀번호입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
