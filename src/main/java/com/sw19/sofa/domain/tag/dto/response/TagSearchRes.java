package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

public record TagSearchRes (
    @Schema(description = "태그 아이디")
    String id,
    @Schema(description = "태그 이름")
    String name,
    @Schema(description = "태그 타입")
    TagType type,
    @Schema(description = "맴버 아이디")
    String memberId
){

    public TagSearchRes(Tag tag) {
        this(tag.getEncryptedId(), tag.getName(), TagType.AI, null);
    }


    public TagSearchRes(CustomTagRes customTag) {
        this(customTag.id(), customTag.name(), TagType.CUSTOM, customTag.memberId());
    }

    public TagSearchRes(CustomTag customTag) {
        this(customTag.getEncryptedId(), customTag.getName(), TagType.CUSTOM, customTag.getMember().getEncryptUserId());
    }
}