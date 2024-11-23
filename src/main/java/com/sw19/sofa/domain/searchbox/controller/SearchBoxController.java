package com.sw19.sofa.domain.searchbox.controller;

import com.sw19.sofa.domain.searchbox.api.SearchBoxApi;
import com.sw19.sofa.domain.searchbox.dto.request.SearchBoxReq;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.service.SearchBoxService;
import com.sw19.sofa.global.common.dto.ListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchBoxController implements SearchBoxApi {
    private final SearchBoxService searchBoxService;

    @Override
    @GetMapping
    public ResponseEntity<ListRes<SearchBoxRes>> search(
            @ModelAttribute SearchBoxReq req,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit
    ) {
        return ResponseEntity.ok(searchBoxService.search(req, lastId, limit));
    }
}

