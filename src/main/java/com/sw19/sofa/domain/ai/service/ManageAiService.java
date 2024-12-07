package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.global.common.dto.TitleAndSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManageAiService {
    private final AiSummaryService aiSummaryService;
    private final AiTagService aiTagService;
    private final WebScrapingService webScrapingService;
    private final AiFolderService aiFolderService;

    public TitleAndSummaryDto createTitleAndSummary(String url) {
        log.info("Extracting title and content from URL: {}", url);
        String content = webScrapingService.extractContent(url);
        String title = webScrapingService.extractTitle(url);
        String summary = aiSummaryService.generateSummary(content);

        return new TitleAndSummaryDto(title, summary);
    }

    public List<String> createTagList(String url) {
        log.info("Generating tags for URL: {}", url);
        String content = webScrapingService.extractContent(url);
        return aiTagService.generateTags(content);
    }

    public String recommendFolder(String content, List<String> existingFolders) {
        return aiFolderService.recommendFolderName(content, existingFolders);
    }
}