package com.sw19.sofa.domain.auth.dto;

public record AuthMemberDto(
        String id,
        String email,
        String name,
        Boolean isNew
) {
}
