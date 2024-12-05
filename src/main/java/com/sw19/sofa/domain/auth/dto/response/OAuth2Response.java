package com.sw19.sofa.domain.auth.dto.response;

public record OAuth2Response (
    String accessToken,
    String refreshToken,
    String tokenType
){
    public OAuth2Response(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}