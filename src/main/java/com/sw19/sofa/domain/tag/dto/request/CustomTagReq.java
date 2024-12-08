package com.sw19.sofa.domain.tag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CustomTagReq (
    @Schema(description = "태그 이름")
    @NotBlank(message = "태그 이름 추가 해주세요.")
    String name
){
}