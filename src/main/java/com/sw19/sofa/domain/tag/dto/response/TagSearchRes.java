package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import lombok.Getter;

@Getter
public class TagSearchRes {
    private String id;
    private String name;
    private TagType type;
    private String memberId;


    public TagSearchRes(String id, String name, TagType type, String memberId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.memberId = memberId;
    }


    public static TagSearchRes fromTag(Tag tag) {
        return new TagSearchRes(
                tag.getEncryptedId(),
                tag.getName(),
                TagType.AI,
                null
        );
    }


    public static TagSearchRes fromCustomTagRes(CustomTagRes customTag) {
        return new TagSearchRes(
                customTag.getId(),
                customTag.getName(),
                TagType.CUSTOM,
                customTag.getMemberId()
        );
    }

    public static TagSearchRes fromCustomTag(CustomTag customTag) {
        return new TagSearchRes(
                customTag.getEncryptedId(),
                customTag.getName(),
                TagType.CUSTOM,
                customTag.getMember().getEncryptUserId()
        );
    }
}