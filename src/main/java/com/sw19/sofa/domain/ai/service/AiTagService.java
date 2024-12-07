package com.sw19.sofa.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiTagService {
    private final OpenAIService openAIService;

    public List<String> generateTags(String content) {
        String prompt = String.format("""
            다음 웹 페이지 내용을 분석하여 3개의 관련 태그를 생성해주세요.
            각 태그는 한 단어로 작성하고, 쉼표로 구분해주세요.
            전문 용어나 고유명사도 포함될 수 있습니다:
            
            %s
            """, content);

        String response = openAIService.sendPrompt(prompt, 100);
        return Arrays.asList(response.split(","));
    }
}