package com.sw19.sofa.security.jwt.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtConstants {
    AUTHORITIES_KEY("Authorization"),
    TOKEN_TYPE("token_type"),
    ACCESS_TOKEN_TYPE("access"),
    REFRESH_TOKEN_TYPE("refresh"),
    BEARER_HEADER("Bearer ")
    ;

    final String value;

}
