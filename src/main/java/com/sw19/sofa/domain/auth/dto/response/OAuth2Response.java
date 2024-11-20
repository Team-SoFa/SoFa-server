package com.sw19.sofa.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Response {
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    public String getTokenValue() {
        return tokenType + " " + accessToken;
    }
}