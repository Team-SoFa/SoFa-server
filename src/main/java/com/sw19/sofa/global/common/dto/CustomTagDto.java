package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.global.util.EncryptionUtil;

public record CustomTagDto(Long id, String name) {
    public CustomTagDto(CustomTag customTag){
        this(customTag.getId(), customTag.getName());
    }
    public String getEncryptId(){
        return EncryptionUtil.encrypt(id);
    }
}
