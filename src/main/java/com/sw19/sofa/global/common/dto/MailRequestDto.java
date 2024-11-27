package com.sw19.sofa.global.common.dto;

import java.util.Map;

public record MailRequestDto(
        String templateName,
        Map<String, Object> data,
        String to,
        String title
) {
}
