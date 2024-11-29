package com.sw19.sofa.global.common.enums;

import com.querydsl.core.types.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOrder {
    @Schema(name = "오름차순")
    ASCENDING(Order.ASC),
    @Schema(name = "내림차순")
    DESCENDING(Order.DESC);
    private final Order order;
}
