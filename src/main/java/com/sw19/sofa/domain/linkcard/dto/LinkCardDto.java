package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardDto(
        @Schema(name = "아이디")
        String id,
        @Schema(name = "제목")
        String title,
        @Schema(name = "메모")
        String memo,
        @Schema(name = "알림 여부")
        Boolean alarm,
        @Schema(name = "아티클")
        LinkCardArticleDto article,
        @Schema(name = "폴더")
        LinkCardFolderDto folder
) {
    public LinkCardDto(LinkCard linkCard) {
        this(linkCard.getEncryptUserId(),
                linkCard.getTitle(),
                linkCard.getMemo(),
                linkCard.getIsAlarm(),
                new LinkCardArticleDto(linkCard.getArticle()),
                new LinkCardFolderDto(linkCard.getFolder())
        );
    }
}
