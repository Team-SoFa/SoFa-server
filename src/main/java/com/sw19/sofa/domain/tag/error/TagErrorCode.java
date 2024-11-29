package com.sw19.sofa.domain.tag.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements ErrorCode {
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "T-001", "존재하지 않는 태그입니다."),
    TAG_ALREADY_EXISTS(HttpStatus.CONFLICT, "T-002", "이미 존재하는 태그입니다."),
    TAG_LIST_EMPTY(HttpStatus.BAD_REQUEST, "T-003", "태그 목록이 비어있습니다."),
    TAG_NOT_OWNED_BY_MEMBER(HttpStatus.FORBIDDEN, "T-004", "해당 태그에 대한 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}