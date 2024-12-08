package com.sw19.sofa.global.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ListRes<T>(
        @Schema(description = "검색 요소")
        List<T> data,
        @Schema(description = "요청 요소 갯수")
        int limit,
        @Schema(description = "응답 요소 갯수")
        int size,
        @Schema(description = "다음 요소 존재 여부")
        boolean hasNext
) {

}
