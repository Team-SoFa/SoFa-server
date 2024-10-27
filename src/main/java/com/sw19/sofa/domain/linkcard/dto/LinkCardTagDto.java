package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.util.EncryptionUtil;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardTagDto(
        @Schema(name = "폴더 아이디") String id, @Schema(name = "폴더명") String name) {
    public LinkCardTagDto(TagDto tagDto){
        this(EncryptionUtil.encrypt(tagDto.id()), tagDto.name());
    }
}
