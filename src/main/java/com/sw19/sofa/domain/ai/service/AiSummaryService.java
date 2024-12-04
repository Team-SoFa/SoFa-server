package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.global.common.service.OpenAIService;
import org.springframework.stereotype.Service;

@Service
public class AiSummaryService {

    private final OpenAIService openAIService;

    public AiSummaryService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public String summarize(String content) {
        String prompt = "다음 내용을 500자 이내로 요약해주세요:\n" + content;
        return openAIService.sendPrompt(prompt, 500);
    }
}
