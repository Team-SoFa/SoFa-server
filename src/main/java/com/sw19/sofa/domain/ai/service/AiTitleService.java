package com.sw19.sofa.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiTitleService {
    private final OpenAIService openAIService;

    public String generateTitle(String content) {
        String prompt = String.format("""
            다음 웹 페이지의 내용을 분석하여 특수 문자를 포함하지 않은 깔끔하고 간결한 제목을 생성해주세요.
            제목은 15자 이내여야 하며, 내용을 정확하게 반영해야 합니다.
            기술 문서의 경우 핵심 기술이나 개념을 포함해주세요:
            
            %s
            """, content);

        return openAIService.sendPrompt(prompt, 50).trim();
    }
}