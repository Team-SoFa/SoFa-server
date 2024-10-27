package com.sw19.sofa.domain.linkcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateLinkCardBasicInfoReq(
        @NotNull @Schema(name = "링크 주소") String url
) {
}
