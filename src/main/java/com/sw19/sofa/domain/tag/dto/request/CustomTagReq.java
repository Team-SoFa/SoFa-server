package com.sw19.sofa.domain.tag.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomTagReq {
    private Long memberId;
    private String name;
}