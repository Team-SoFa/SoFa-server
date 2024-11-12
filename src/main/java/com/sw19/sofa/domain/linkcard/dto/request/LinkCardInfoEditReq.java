package com.sw19.sofa.domain.linkcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LinkCardInfoEditReq(
        @Schema(description = "제목")
        @NotBlank(message = "링크 카드 제목이 필요합니다")
        String title,
        @Schema(description = "메모")
        String memo,
        @Schema(description = "AI 요약")
        String summary
) {
}
