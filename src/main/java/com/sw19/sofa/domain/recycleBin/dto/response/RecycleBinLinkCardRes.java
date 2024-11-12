package com.sw19.sofa.domain.recycleBin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record RecycleBinLinkCardRes(
        @Schema(description = "링크카드")
        String id,
        @Schema(description = "이미지")
        String imageUrl,
        @Schema(description = "제목")
        String title,
        @Schema(description = "링크")
        String url,
        @Schema(description = "삭제 날짜")
        LocalDateTime deleteTime
) {
}
