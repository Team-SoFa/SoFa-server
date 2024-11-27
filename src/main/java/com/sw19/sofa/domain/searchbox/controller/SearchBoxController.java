package com.sw19.sofa.domain.searchbox.controller;

import com.sw19.sofa.domain.searchbox.api.SearchBoxApi;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.domain.searchbox.service.SearchBoxService;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchBoxController implements SearchBoxApi {
    private final SearchBoxService searchBoxService;

    @Override
    @GetMapping("/folder/{folderId}")
    public ResponseEntity<ListRes<SearchBoxRes>> searchByFolder(
            @PathVariable String folderId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchByFolder(folderId, keyword, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/tags")
    public ResponseEntity<ListRes<SearchBoxRes>> searchByTags(
            @RequestParam List<String> tagIds,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchByTags(tagIds, keyword, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/tags-folder")
    public ResponseEntity<ListRes<SearchBoxRes>> searchByTagsAndFolder(
            @RequestParam List<String> tagIds,
            @RequestParam String folderId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchByTagsAndFolder(tagIds, folderId, keyword, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<ListRes<SearchBoxRes>> searchAllLinkCards(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchAllLinkCards(keyword, lastId, limit, sortBy, sortOrder));
    }
}