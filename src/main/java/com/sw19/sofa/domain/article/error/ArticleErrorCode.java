package com.sw19.sofa.domain.article.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ArticleErrorCode implements ErrorCode {
    NOT_FOUND_ARTICLE(HttpStatus.NOT_FOUND, "AR-001", "기본 정보를 찾지 못했습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
