package com.sw19.sofa.domain.searchbox.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.global.common.enums.SortOrder;

import java.util.List;

public interface SearchBoxCustomRepository {
    List<LinkCard> searchByFolder(
            Long folderId,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );

    List<LinkCard> searchByTags(
            List<Long> tagIds,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );

    List<LinkCard> searchAll(
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );
}