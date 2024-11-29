package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import lombok.Getter;

@Getter
public class TagSearchRes {
    private Long id;
    private String name;
    private String encryptedId;
    private TagType type;
    private Long memberId;


    public TagSearchRes(Long id, String name, String encryptedId, TagType type, Long memberId) {
        this.id = id;
        this.name = name;
        this.encryptedId = encryptedId;
        this.type = type;
        this.memberId = memberId;
    }


    public static TagSearchRes fromTag(Tag tag) {
        return new TagSearchRes(
                tag.getId(),
                tag.getName(),
                tag.getEncryptedId(),
                TagType.AI,
                null
        );
    }


    public static TagSearchRes fromCustomTag(CustomTag customTag) {
        return new TagSearchRes(
                customTag.getId(),
                customTag.getName(),
                customTag.getEncryptedId(),
                TagType.CUSTOM,
                customTag.getMember().getId()
        );
    }
}