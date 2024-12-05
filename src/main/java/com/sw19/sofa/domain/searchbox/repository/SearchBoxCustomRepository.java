package com.sw19.sofa.domain.searchbox.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;

import java.util.List;

public interface SearchBoxCustomRepository {
    List<LinkCard> searchByFolder(
            Long folderId,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );

    List<LinkCard> searchByTags(
            List<Long> tagIds,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );

    List<LinkCard> searchByTagsAndFolder(
            List<Long> tagIds,
            Long folderId,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );

    List<LinkCard> searchAll(
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    );
}