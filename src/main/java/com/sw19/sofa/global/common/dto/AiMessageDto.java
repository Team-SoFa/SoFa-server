package com.sw19.sofa.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiMessageDto {
    private String role;
    private String content; //prompt를 뜻함

}