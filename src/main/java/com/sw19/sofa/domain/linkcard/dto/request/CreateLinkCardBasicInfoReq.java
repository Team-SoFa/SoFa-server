package com.sw19.sofa.domain.linkcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateLinkCardBasicInfoReq(
        @NotBlank(message = "링크 주소가 필요합니다.") @Schema(name = "링크 주소") String url
) {
}
