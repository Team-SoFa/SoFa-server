package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import io.swagger.v3.oas.annotations.media.Schema;

public record CustomTagRes (
        @Schema(description = "태그 아이디")
        String id,
        @Schema(description = "태그 이름")
        String name,
        @Schema(description = "맴버 아이디")
        String memberId
){
    public CustomTagRes(CustomTag customTag) {
        this(customTag.getName(), customTag.getEncryptedId(), customTag.getMember().getEncryptUserId());
    }
}