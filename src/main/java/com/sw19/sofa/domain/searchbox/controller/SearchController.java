package com.sw19.sofa.domain.searchbox.controller;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.searchbox.dto.SearchCriteria;
import com.sw19.sofa.domain.searchbox.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<LinkCard> search(@RequestBody SearchCriteria criteria) {
        return searchService.searchLinkCards(criteria);
    }
}
