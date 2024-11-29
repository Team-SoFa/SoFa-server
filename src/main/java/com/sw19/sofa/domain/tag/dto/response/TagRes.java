package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.Tag;
import lombok.Getter;

@Getter
public class TagRes {
    private final String name;
    private final String encryptedId;

    public TagRes(Tag tag) {
        this.name = tag.getName();
        this.encryptedId = tag.getEncryptedId();
    }
}