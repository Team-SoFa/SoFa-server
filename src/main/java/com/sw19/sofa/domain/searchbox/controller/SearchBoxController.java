package com.sw19.sofa.domain.searchbox.controller;

import com.sw19.sofa.domain.searchbox.api.SearchBoxApi;
import com.sw19.sofa.domain.searchbox.dto.request.SearchBoxReq;
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
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchByFolder(folderId, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/tags")
    public ResponseEntity<ListRes<SearchBoxRes>> searchByTags(
            @RequestParam List<String> tagIds,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchByTags(tagIds, lastId, limit, sortBy, sortOrder));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<ListRes<SearchBoxRes>> searchAllLinkCards(
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(searchBoxService.searchAllLinkCards(lastId, limit, sortBy, sortOrder));
    }
}
