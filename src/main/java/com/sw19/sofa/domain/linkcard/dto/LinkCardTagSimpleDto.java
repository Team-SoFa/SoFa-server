package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardTagSimpleDto(
        @Schema(description = "태그 아이디") Long id,
        @Schema(description = "태그 속성", example = "AI/CUSTOM") TagType tagType
) {
    public LinkCardTagSimpleDto(LinkCardTag linkCardTag){
        this(linkCardTag.getTagId(), linkCardTag.getTagType());
    }
    public LinkCardTagSimpleDto(LinkCardTagSimpleEncryptDto dto){this(dto.getDecryptionId(), dto.tagType());}
}
