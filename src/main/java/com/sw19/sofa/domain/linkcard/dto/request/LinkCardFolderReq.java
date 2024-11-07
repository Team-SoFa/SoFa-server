package com.sw19.sofa.domain.linkcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardFolderReq(
        @Schema(description = "폴더 아이디") String id
) {
}
