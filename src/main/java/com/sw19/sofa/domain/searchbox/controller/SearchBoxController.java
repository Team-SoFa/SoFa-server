package com.sw19.sofa.domain.searchbox.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.searchbox.api.SearchBoxApi;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.domain.searchbox.service.SearchBoxService;
import com.sw19.sofa.domain.searchbox.service.SearchHistoryService;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.enums.SortOrder;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchBoxController implements SearchBoxApi {
    private final SearchBoxService searchBoxService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping
    public ResponseEntity<ListRes<SearchBoxRes>> search(
            @RequestParam(required = false) String folderId,
            @RequestParam(required = false) List<String> tagIds,
            @RequestParam(required = false) String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.search(folderId, tagIds, keyword, member, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/history/keywords")
    public ResponseEntity<List<String>> getRecentSearchKeywords(@AuthMember Member member) {
        return ResponseEntity.ok(searchHistoryService.getSearchKeywordHistory(member.getId()));
    }

    @Override
    @GetMapping("/history/tags")
    public ResponseEntity<List<String>> getRecentSearchTags(@AuthMember Member member) {
        return ResponseEntity.ok(searchHistoryService.getSearchTagHistory(member.getId()));
    }

    @Override
    @GetMapping("/tags")
    public ResponseEntity<List<TagSearchRes>> searchTags(@RequestParam String keyword) {
        return ResponseEntity.ok(searchBoxService.searchTags(keyword));
    }
}
