package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.global.common.dto.CustomTagDto;
import com.sw19.sofa.global.common.dto.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardTagDto(
        @Schema(description = "태그 아이디") String id,
        @Schema(description = "태그명") String name,
        @Schema(description = "태그 속성", example = "AI/CUSTOM") TagType tagType
) {
    public LinkCardTagDto(TagDto tagDto){
        this(tagDto.getEncryptId(), tagDto.name(), TagType.AI);
    }
    public LinkCardTagDto(CustomTagDto customTagDto){
        this(customTagDto.getEncryptId(), customTagDto.name(), TagType.CUSTOM);
    }
}
