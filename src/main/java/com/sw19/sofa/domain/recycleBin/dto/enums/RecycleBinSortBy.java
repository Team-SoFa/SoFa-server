package com.sw19.sofa.domain.recycleBin.dto.enums;

import com.querydsl.core.types.OrderSpecifier;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;

public enum RecycleBinSortBy implements SortBy {
    @Schema(description = "최근 삭제순")
    RECENTLY_DELETE{
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.modifiedAt);
        }
    },
    @Schema(description = "이름순")
    NAME{
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.title);
        }
    }
}
