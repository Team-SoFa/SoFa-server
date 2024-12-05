package com.sw19.sofa.domain.ai.dto.request;

import com.sw19.sofa.global.common.dto.AiMessageDto;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {
    private String model;
    private List<AiMessageDto> messages;
    private int max_tokens;

    public ChatGPTRequest(String model, String prompt, int maxTokens) {
        this.model = model;
        this.max_tokens = maxTokens;
        this.messages = new ArrayList<>();
        this.messages.add(new AiMessageDto("user", prompt));
    }
}
