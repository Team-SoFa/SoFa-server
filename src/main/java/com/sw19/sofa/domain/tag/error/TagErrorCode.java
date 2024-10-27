package com.sw19.sofa.domain.tag.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements ErrorCode {
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "T-001", "존재하지 않는 태그입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
