package com.sw19.sofa.domain.recycleBin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RecycleReq(
        @Schema(description = "복원할 폴더 아이디")
        @NotBlank
        String folderId
) {
}
