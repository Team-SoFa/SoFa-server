package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardSimpleRes(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "주소")
        String url,
        @Schema(description = "이미지 주소")
        String imageUrl
) {
        public LinkCardSimpleRes(LinkCard linkCard) {
                this(linkCard.getEncryptId(), linkCard.getTitle(), linkCard.getArticle().getUrl(), linkCard.getArticle().getImageUrl());
        }
}
