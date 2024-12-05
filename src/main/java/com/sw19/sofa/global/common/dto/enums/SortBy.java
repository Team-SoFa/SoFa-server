package com.sw19.sofa.global.common.dto.enums;

import com.querydsl.core.types.OrderSpecifier;
import com.sw19.sofa.domain.linkcard.entity.QLinkCard;


public interface SortBy {
    QLinkCard linkCard = QLinkCard.linkCard;

    OrderSpecifier<?> getOrderSpecifier(SortOrder sortOrder);
}
