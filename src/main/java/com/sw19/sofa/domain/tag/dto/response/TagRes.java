package com.sw19.sofa.domain.tag.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TagRes (

        @Schema(description = "태그 아이디")
        String id,
        @Schema(description = "태그 이름")
        String name
){
}