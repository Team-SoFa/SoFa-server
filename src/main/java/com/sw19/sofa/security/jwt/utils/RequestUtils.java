package com.sw19.sofa.security.jwt.utils;

import jakarta.servlet.http.HttpServletRequest;

import static com.sw19.sofa.security.jwt.constants.JwtConstants.AUTHORITIES_KEY;
import static com.sw19.sofa.security.jwt.constants.JwtConstants.BEARER_HEADER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public abstract class RequestUtils {
    public static boolean isContainsAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORITIES_KEY.getValue());
        return authorization != null
                && authorization.startsWith(BEARER_HEADER.getValue());
    }

    // 유효한 Authorization Bearer Token에서 AccessToken 만 뽑아오기
    public static String getAuthorizationAccessToken(HttpServletRequest request) {
        // "Bearer " 문자열 제외하고 뽑아오기
        return request.getHeader(AUTHORIZATION).substring(7);
    }

}