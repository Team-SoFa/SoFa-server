package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.tag.entity.Tag;

public record TagDto(Long id, String name) {
    public TagDto(Tag tag){
        this(tag.getId(), tag.getName());
    }
}
