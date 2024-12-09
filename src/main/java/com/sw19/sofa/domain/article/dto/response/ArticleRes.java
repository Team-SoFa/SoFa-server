package com.sw19.sofa.domain.article.dto.response;

import com.sw19.sofa.domain.article.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;

public record ArticleRes(
        @Schema(description = "제목")
        String title,
        @Schema(description = "링크 주소")
        String url,
        @Schema(description = "이미지 주소")
        String imageUrl
) {
        public ArticleRes(Article article) {
                this(article.getTitle(), article.getUrl(), article.getImageUrl());
        }
}
