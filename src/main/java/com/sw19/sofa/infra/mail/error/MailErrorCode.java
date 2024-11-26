package com.sw19.sofa.infra.mail.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements ErrorCode {
    FAILED_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR,"M-000","메일 전송에 실패했습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
