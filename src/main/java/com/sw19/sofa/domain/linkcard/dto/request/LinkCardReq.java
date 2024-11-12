package com.sw19.sofa.domain.linkcard.dto.request;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record LinkCardReq(
        @Schema(description = "제목")
        @NotBlank(message = "링크카드 제목이 필요합니다")
        String title,
        @Schema(description = "링크 주소")
        @NotBlank(message = "링크 주소가 필요합니다")
        String url,
        @Schema(description = "폴더 ID")
        @NotBlank(message = "폴더 ID가 필요합니다")
        String folderId,
        @Schema(description = "태그 리스트")
        List<LinkCardTagSimpleDto> tagList,
        @Schema(description = "메모")
        String memo,
        @Schema(description = "AI 요약")
        String summary
) {
}
