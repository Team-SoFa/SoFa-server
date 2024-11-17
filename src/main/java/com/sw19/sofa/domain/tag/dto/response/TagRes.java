package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.Tag;
import lombok.Getter;

@Getter
public class TagRes {
    private Long id;
    private String name;
    private String encryptedId;

    public TagRes(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.encryptedId = tag.getEncryptUserId();
    }
}