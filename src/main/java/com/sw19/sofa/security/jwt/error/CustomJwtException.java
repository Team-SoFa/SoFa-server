package com.sw19.sofa.security.jwt.error;

import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.security.jwt.error.code.JwtErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtException extends BusinessException {
    public CustomJwtException(JwtErrorCode errorCode) {
        super(errorCode);
    }
}