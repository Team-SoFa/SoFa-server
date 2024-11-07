package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.article.entity.ArticleTag;
import io.swagger.v3.oas.annotations.media.Schema;

public record ArticleTagDto(
        @Schema(description = "아이디")
        Long id,
        @Schema(description = "태그 아이디")
        Long tagId
) {
    public ArticleTagDto(ArticleTag articleTag) {
        this(articleTag.getId(), articleTag.getTag().getId());
    }
}
