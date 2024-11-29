package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import lombok.Getter;

@Getter
public class CustomTagRes {
    private final String name;
    private final String encryptedId;
    private final Long memberId;

    public CustomTagRes(CustomTag customTag) {
        this.name = customTag.getName();
        this.encryptedId = customTag.getEncryptedId();
        this.memberId = customTag.getMember().getId();
    }
}