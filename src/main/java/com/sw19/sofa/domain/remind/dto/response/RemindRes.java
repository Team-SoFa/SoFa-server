package com.sw19.sofa.domain.remind.dto.response;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.global.util.EncryptionUtil;

import java.time.LocalDateTime;

public record RemindRes(
        String encryptedId,
        String encryptedLinkCardId,
        String title,
        String url,
        String summary,
        String memo,
        String imageUrl,
        long views,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDateTime visitedAt
) {
    public static RemindRes from(Remind remind) {
        LinkCard linkCard = remind.getLinkCard();
        return new RemindRes(
                EncryptionUtil.encrypt(remind.getId()),
                EncryptionUtil.encrypt(linkCard.getId()),
                linkCard.getTitle(),
                linkCard.getArticle().getUrl(),
                linkCard.getSummary(),
                linkCard.getMemo(),
                linkCard.getArticle().getImageUrl(),
                linkCard.getViews(),
                linkCard.getCreatedAt(),
                linkCard.getModifiedAt(),
                linkCard.getVisitedAt()
        );
    }
}