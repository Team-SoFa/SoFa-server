package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.dto.enums.TagType;
import com.sw19.sofa.global.common.dto.MemberTagDto;
import com.sw19.sofa.global.common.dto.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardTagDto(
        @Schema(name = "태그 아이디") String id,
        @Schema(name = "태그명") String name,
        @Schema(name = "태그 속성") TagType tagType
) {
    public LinkCardTagDto(TagDto tagDto){
        this(tagDto.getEncryptId(), tagDto.name(), TagType.AI);
    }

    public LinkCardTagDto(MemberTagDto memberTagDto){
        this(memberTagDto.getEncryptId(), memberTagDto.name(), TagType.Member);
    }
}
