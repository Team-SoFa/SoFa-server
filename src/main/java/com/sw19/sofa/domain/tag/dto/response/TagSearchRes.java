package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.Getter;

@Getter
public class TagSearchRes {
    private String id;
    private String name;
    private String encryptedId;
    private TagType type;
    private String memberId;


    public TagSearchRes(Long id, String name, String encryptedId, TagType type, Long memberId) {
        this.id = id != null ? EncryptionUtil.encrypt(id) : null;
        this.name = name;
        this.encryptedId = encryptedId;
        this.type = type;
        this.memberId = memberId != null ? EncryptionUtil.encrypt(memberId) : null;
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