package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MostTagLinkCardListRes(

        @Schema(description = "최다 태그 정보")
        LinkCardTagDto tag,
        @Schema(description = "링크카드 리스트")
        List<LinkCardSimpleRes> data,
        @Schema(description = "요청 요소 갯수")
        int limit,
        @Schema(description = "응답 요소 갯수")
        int size,
        @Schema(description = "다음 요소 존재 여부")
        boolean hasNext
) {
}
