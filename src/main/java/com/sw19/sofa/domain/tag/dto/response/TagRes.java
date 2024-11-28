package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;

import lombok.Getter;

@Getter
public class TagRes {
    private Long id;
    private String name;
    private String encryptedId;
    private TagType type;

    public TagRes(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.encryptedId = tag.getEncryptedId();  // 변경
        this.type = tag.getType();
    }

    public TagRes(CustomTag customTag) {
        this.id = customTag.getId();
        this.name = customTag.getName();
        this.encryptedId = customTag.getEncryptedId();
        this.type = TagType.CUSTOM;
    }
}