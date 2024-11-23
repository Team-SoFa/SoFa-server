package com.sw19.sofa.domain.auth.exception;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OAuth2ErrorCode implements ErrorCode {
    AUTHENTICATION_PROCESSING_ERROR(HttpStatus.UNAUTHORIZED, "O-001", "OAuth2 인증 처리 중 오류가 발생했습니다."),
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "O-002", "OAuth2 액세스 토큰을 가져오는데 실패했습니다."),
    USER_INFO_RESPONSE_ERROR(HttpStatus.UNAUTHORIZED, "O-003", "OAuth2 사용자 정보를 가져오는데 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}