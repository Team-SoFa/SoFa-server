package com.sw19.sofa.domain.linkcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record LinkCardReq(
        @Schema(name = "제목")
        @NotBlank(message = "링크 주소가 필요합니다")
        String title,
        @Schema(name = "링크 주소")
        @NotBlank(message = "링크 주소가 필요합니다")
        String url,
        @Schema(name = "폴더 ID")
        @NotBlank(message = "폴더 ID가 필요합니다")
        String folderId,
        @Schema(name = "Member 타입 태그 ID 리스트")
        List<String> tagList,
        @Schema(name = "이미지 주소")
        @NotBlank(message = "이미지 주소가 필요합니다")
        String imageUrl,
        @Schema(name = "AI 요약")
        String summary,
        @Schema(name = "메모")
        String memo,
        @Schema(name = "알림 여부")
        Boolean isAlarm
) {
}
