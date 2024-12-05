package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;

import java.util.List;

public interface LinkCardCustomRepository {
    List<LinkCard> findAllByFolderIdAndSortCondition(List<Long> folderIdList, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId);
}
