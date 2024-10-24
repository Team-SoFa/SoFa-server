package com.sw19.sofa.domain.folder.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FolderErrorCode implements ErrorCode {
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "F-001", "존재하지 않는 폴더입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
