package com.sw19.sofa.domain.ai.controller;

import com.sw19.sofa.domain.ai.service.AiSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@Slf4j
@RequiredArgsConstructor
public class AiSummaryController {
    private final AiSummaryService aiSummaryService;

    @GetMapping("/summarize")
    public String summarize(@RequestParam String content) {
        log.info("Received content for summarization: {}", content);
        return aiSummaryService.generateSummary(content);
    }
}