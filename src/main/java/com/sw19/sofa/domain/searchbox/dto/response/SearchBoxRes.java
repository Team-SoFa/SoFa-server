package com.sw19.sofa.domain.searchbox.dto.response;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.global.common.dto.TagDto;
import java.time.LocalDateTime;
import java.util.List;

public record SearchBoxRes(
        Long id,
        String title,
        String url,
        String summary,
        String memo,
        String imageUrl,
        LocalDateTime createdAt,
        List<TagDto> tags
) {
    public SearchBoxRes(LinkCard linkCard, List<TagDto> tags) {
        this(
                linkCard.getId(),
                linkCard.getTitle(),
                linkCard.getArticle().getUrl(),
                linkCard.getSummary(),
                linkCard.getMemo(),
                linkCard.getArticle().getImageUrl(),
                linkCard.getCreatedAt(),
                tags
        );
    }
}