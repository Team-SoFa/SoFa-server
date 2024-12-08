package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record LinkCardRes(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "링크 주소")
        String url,
        @Schema(description = "이미지 주소")
        String imageUrl,
        @Schema(description = "AI 요약")
        String summary,
        @Schema(description = "메모")
        String memo,
        @Schema(description = "폴더")
        LinkCardFolderDto folder,
        @Schema(description = "태그 리스트")
        List<LinkCardTagDto> tagList

) {
        public LinkCardRes(LinkCard linkCard, List<LinkCardTagDto> linkCardTagDtoList){
                this(
                        linkCard.getEncryptId(),
                        linkCard.getTitle(),
                        linkCard.getArticle().getUrl(),
                        linkCard.getArticle().getImageUrl(),
                        linkCard.getSummary(),
                        linkCard.getMemo(),
                        new LinkCardFolderDto(linkCard.getFolder()),
                        linkCardTagDtoList
                );
        }
}
