package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.article.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;

public record ArticleDto(
        @Schema(description = "아이디")
        Long id,
        @Schema(description = "링크 주소")
        String url,
        @Schema(description = "이미지 주소")
        String imageUrl,
        @Schema(description = "제목")
        String title,
        @Schema(description = "AI 요약")
        String summary,
        @Schema(description = "조회수")
        Long views
) {
    public ArticleDto(Article article) {
        this(article.getId(), article.getUrl(), article.getImageUrl(), article.getTitle(), article.getSummary(), article.getViews());
    }
}
