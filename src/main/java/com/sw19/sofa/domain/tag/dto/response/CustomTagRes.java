package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import lombok.Getter;

@Getter
public class CustomTagRes {
    private Long id;
    private String name;
    private String encryptedId;

    public CustomTagRes(CustomTag customTag) {
        this.id = customTag.getId();
        this.name = customTag.getName();
        this.encryptedId = customTag.getEncryptedId();
    }
}