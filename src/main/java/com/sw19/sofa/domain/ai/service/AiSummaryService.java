package com.sw19.sofa.domain.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiSummaryService {
    private final OpenAIService openAIService;
    private final WebScrapingService webScrapingService;

    public String summarizeUrl(String url) {
        // URL에서 콘텐츠 추출
        String content = webScrapingService.extractContent(url);

        // OpenAI에 요청할 프롬프트 구성
        String prompt = String.format("""
            다음 웹 페이지의 내용을 500자 이내로 요약해주세요. 
            중요한 핵심 내용만 간단명료하게 요약해주세요:
            
            %s
            """, content);

        return openAIService.sendPrompt(prompt, 500);
    }
}