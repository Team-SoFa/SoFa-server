package com.sw19.sofa.domain.linkcard.dto.request;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleEncryptDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record LinkCardTagListReq(
        @Schema(description = "태그 아이디 및 타입 목록") List<LinkCardTagSimpleEncryptDto> tagList
) {
}
