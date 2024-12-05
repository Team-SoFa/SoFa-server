package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.global.util.EncryptionUtil;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardTagSimpleEncryptDto(
        @Schema(description = "태그 아이디") String id,
        @Schema(description = "태그 속성", example = "AI/CUSTOM") TagType tagType
) {
    public Long getDecryptionId(){
        return EncryptionUtil.decrypt(id);
    }
}
