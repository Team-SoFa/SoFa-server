package com.sw19.sofa.domain.remind.repository;

import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;

import java.util.List;

public interface RemindCustomRepository {
    List<Remind> search(
            Long memberId,
            Long lastId,
            int limit,
            RemindSortBy sortBy,
            SortOrder sortOrder
    );
}
