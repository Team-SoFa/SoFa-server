package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.domain.ai.dto.request.ChatGPTRequest;
import com.sw19.sofa.domain.ai.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;
    private final String model;

    public OpenAIService(RestTemplate restTemplate,
                         @Value("${openai.api.url}") String apiUrl,
                         @Value("${openai.api.key}") String apiKey,
                         @Value("${openai.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.model = model;
    }

    public String sendPrompt(String prompt, int maxTokens) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt, maxTokens);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

        ChatGPTResponse response = restTemplate.postForObject(apiUrl, entity, ChatGPTResponse.class);

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getContent();
        }

        throw new RuntimeException("OpenAI 응답이 비정상적입니다.");
    }
}
