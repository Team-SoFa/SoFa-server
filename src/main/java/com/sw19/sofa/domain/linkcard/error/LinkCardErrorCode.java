package com.sw19.sofa.domain.linkcard.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LinkCardErrorCode implements ErrorCode {
    NOT_FOUND_LINK_CARD(HttpStatus.NOT_FOUND, "L-001", "존재하지 않는 링크카드입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
