package com.sw19.sofa.global.common.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum SortBy {
    @Schema(name = "최근 저장순")
    RECENTLY_SAVED,
    @Schema(name = "최근 조회순")
    RECENTLY_VIEWED,
    @Schema(name = "최다 조회순")
    MOST_VIEWED
}
