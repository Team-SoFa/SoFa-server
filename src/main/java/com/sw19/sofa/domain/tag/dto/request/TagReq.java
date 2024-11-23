package com.sw19.sofa.domain.tag.dto.request;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagReq {
    private String name;
    private TagType type;  
}