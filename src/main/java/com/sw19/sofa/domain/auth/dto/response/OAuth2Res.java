package com.sw19.sofa.domain.auth.dto.response;

public record OAuth2Res(
        TokenRes token,
        Boolean isNew
) {
}
