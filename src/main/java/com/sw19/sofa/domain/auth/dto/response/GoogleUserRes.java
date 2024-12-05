package com.sw19.sofa.domain.auth.dto.response;

public record GoogleUserRes(
    String id,
    String email,
    Boolean verified_email,
    String name,
    String given_name,
    String family_name,
    String picture,
    String locale
){
}