package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import lombok.Getter;

@Getter
public class CustomTagRes {
    private final String name;
    private final String id;
    private final String memberId;  // Long -> String으로 변경

    public CustomTagRes(CustomTag customTag) {
        this.name = customTag.getName();
        this.id = customTag.getEncryptedId();
        this.memberId = customTag.getMember().getId() != null ?
                customTag.getMember().getEncryptUserId() : null;
    }
}