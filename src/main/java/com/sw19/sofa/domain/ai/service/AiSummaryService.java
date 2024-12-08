package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.global.common.dto.TitleAndSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiSummaryService {
    private final OpenAIService openAIService;
    private final WebScrapingService webScrapingService;
    private final AiTitleService aiTitleService;

    public String generateSummary(String content) {
        String prompt = String.format("""
            무조건 다음 웹 페이지의 내용을 300자 이하로 간단명료하게 요약해주세요. 요약은 주요 내용을 중심으로 작성하고, 150자 이상이어야 합니다 :
            
            %s
            """, content);

        return openAIService.sendPrompt(prompt, 500);
    }

    public TitleAndSummaryDto generateTitleAndSummary(String url) {
        String content = webScrapingService.extractContent(url);
        String title = aiTitleService.generateTitle(content);
        String summary = generateSummary(content);

        return new TitleAndSummaryDto(title, summary);
    }
}