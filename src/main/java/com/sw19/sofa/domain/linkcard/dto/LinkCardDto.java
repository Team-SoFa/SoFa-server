package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record LinkCardDto(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "메모")
        String memo,
        @Schema(description = "생성 시간")
        LocalDateTime createdAt,
        @Schema(description = "수정 시간")
        LocalDateTime updateAt,
        @Schema(description = "생성 시간")
        LocalDateTime visitedAt,
        @Schema(description = "아티클")
        LinkCardArticleDto article,
        @Schema(description = "폴더")
        LinkCardFolderDto folder
) {
    public LinkCardDto(LinkCard linkCard) {
        this(linkCard.getEncryptId(),
                linkCard.getTitle(),
                linkCard.getMemo(),
                linkCard.getCreatedAt(),
                linkCard.getModifiedAt(),
                linkCard.getVisitedAt(),
                new LinkCardArticleDto(linkCard.getArticle()),
                new LinkCardFolderDto(linkCard.getFolder())
        );
    }
}
