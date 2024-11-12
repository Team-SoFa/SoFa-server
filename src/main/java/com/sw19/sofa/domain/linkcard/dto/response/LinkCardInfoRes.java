package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardInfoRes(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "AI 요약")
        String summary,
        @Schema(description = "메모")
        String memo

) {
        public LinkCardInfoRes(LinkCard linkCard){
                this(
                        linkCard.getEncryptId(),
                        linkCard.getTitle(),
                        linkCard.getSummary(),
                        linkCard.getMemo()
                );
        }
}
