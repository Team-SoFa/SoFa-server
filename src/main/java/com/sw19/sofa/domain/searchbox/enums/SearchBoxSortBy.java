package com.sw19.sofa.domain.searchbox.enums;

import com.querydsl.core.types.OrderSpecifier;
import com.sw19.sofa.global.common.enums.SortBy;
import com.sw19.sofa.global.common.enums.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;

public enum SearchBoxSortBy implements SortBy {
    @Schema(name = "정확도순")
    ACCURACY {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.id);
        }
    },
    @Schema(name = "최근 수정순")
    RECENTLY_MODIFIED {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.modifiedAt);
        }
    },
    @Schema(name = "최근 저장순")
    RECENTLY_SAVED {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.createdAt);
        }
    },
    @Schema(name = "최근 방문순")
    RECENTLY_VIEWED {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.visitedAt);
        }
    },
    @Schema(name = "최다 방문순")
    MOST_VIEWED {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.views);
        }
    },
    @Schema(name = "이름순")
    NAME {
        @Override
        public OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder) {
            return new OrderSpecifier<>(sortOrder.getOrder(), linkCard.title);
        }
    }
}