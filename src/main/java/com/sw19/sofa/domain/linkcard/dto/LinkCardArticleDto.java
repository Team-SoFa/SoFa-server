package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.article.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardArticleDto(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "링크 주소")
        String url,
        @Schema(description = "이미지 주소")
        String imageUrl,
        @Schema(description = "AI 요약")
        String summary
) {
    public LinkCardArticleDto(Article article) {
        this(article.getEncryptUserId(), article.getUrl(), article.getImageUrl(), article.getSummary());
    }
}
