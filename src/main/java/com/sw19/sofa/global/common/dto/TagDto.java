package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.global.util.EncryptionUtil;

public record TagDto(Long id, String name) {
    public TagDto(Tag tag){
        this(tag.getId(), tag.getName());
    }

    public String getEncryptId(){
        return EncryptionUtil.encrypt(id);
    }
}
