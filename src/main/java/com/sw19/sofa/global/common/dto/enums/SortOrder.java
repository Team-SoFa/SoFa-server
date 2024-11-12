package com.sw19.sofa.global.common.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum SortOrder {
    @Schema(name = "오름차순")
    ASCENDING,
    @Schema(name = "내림차순")
    DESCENDING
}
