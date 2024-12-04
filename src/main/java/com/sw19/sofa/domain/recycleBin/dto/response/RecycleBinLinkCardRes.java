package com.sw19.sofa.domain.recycleBin.dto.response;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record RecycleBinLinkCardRes(
        @Schema(description = "링크카드 아이디")
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
        public RecycleBinLinkCardRes(LinkCard linkCard) {
                this(linkCard.getEncryptId(), linkCard.getArticle().getImageUrl(), linkCard.getTitle(), linkCard.getArticle().getUrl(), linkCard.getModifiedAt());
        }
}
