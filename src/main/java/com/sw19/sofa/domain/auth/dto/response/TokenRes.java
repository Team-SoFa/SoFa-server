package com.sw19.sofa.domain.auth.dto.response;

public record TokenRes(
    String accessToken,
    String refreshToken,
    String tokenType
){
    public TokenRes(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}