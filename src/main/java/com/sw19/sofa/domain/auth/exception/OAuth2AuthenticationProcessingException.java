package com.sw19.sofa.domain.auth.exception;

import com.sw19.sofa.global.error.code.ErrorCode;
import com.sw19.sofa.global.error.exception.BusinessException;

public class OAuth2AuthenticationProcessingException extends BusinessException {
    public OAuth2AuthenticationProcessingException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OAuth2AuthenticationProcessingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

//    public OAuth2AuthenticationProcessingException(String message) {
//        super(message, OAuth2ErrorCode.AUTHENTICATION_PROCESSING_ERROR);
//    }
}
