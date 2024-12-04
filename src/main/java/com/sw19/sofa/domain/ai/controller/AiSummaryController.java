package com.sw19.sofa.domain.ai.controller;

import com.sw19.sofa.domain.ai.service.AiSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiSummaryController {

    private final AiSummaryService aiSummaryService;

    public AiSummaryController(AiSummaryService aiSummaryService) {
        this.aiSummaryService = aiSummaryService;
    }

    @GetMapping("/summarize")
    public String summarize(@RequestParam String content) {
        return aiSummaryService.summarize(content);
    }
}
