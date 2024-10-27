package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateLinkCardBasicInfoRes(
        @Schema(name = "제목") String title, @Schema(name = "AI 요약") String summary, @Schema(name = "태그 리스트") List<LinkCardTagDto> tags, @Schema(name = "폴더") LinkCardFolderDto folder) {
}
