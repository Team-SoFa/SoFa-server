package com.sw19.sofa.domain.searchbox.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchBoxErrorCode implements ErrorCode {
    FOLDER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "S-001", "폴더 접근 권한이 없습니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "S-002", "태그를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}