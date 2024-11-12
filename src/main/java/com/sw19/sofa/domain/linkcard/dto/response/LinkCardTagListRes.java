package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;

import java.util.List;

public record LinkCardTagListRes(
        List<LinkCardTagDto> tagList
) {
}
