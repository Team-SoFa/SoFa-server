package com.sw19.sofa.global.common.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private String content;
    }
}
