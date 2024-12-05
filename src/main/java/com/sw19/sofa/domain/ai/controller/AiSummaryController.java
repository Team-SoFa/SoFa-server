package com.sw19.sofa.domain.ai.controller;

import com.sw19.sofa.domain.ai.service.AiSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiSummaryController {

    private final AiSummaryService aiSummaryService;

    @GetMapping("/summarize")
    public String summarizeUrl(@RequestParam String url) {
        return aiSummaryService.summarizeUrl(url);
    }
}