package com.sw19.sofa.domain.linkcard.api;

import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.CreateLinkCardBasicInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "\uD83D\uDD17 LinkCard")
public interface LinkCardApi {

    @Operation(summary = "링크 카드 AI 정보 생성", description = "AI를 통해 전달 받은 링크에 대한 기본 링크 카드 정보를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 기본 정보(제목, AI 요약, 폴더, 태그)")
    })
    ResponseEntity<CreateLinkCardBasicInfoRes> createLinkCardBasicInfo(Member member, CreateLinkCardBasicInfoReq req);

    @Operation(summary = "링크 카드 조회", description = "링크 카드를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 정보")
    })
    ResponseEntity<LinkCardRes> getLinkCard(String id);

    @Operation(summary = "링크 카드 추가", description = "링크 카드를 추가합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 추가 완료"),
            @ApiResponse(responseCode = "404", description = "code: AR-001 | message: 기본 정보를 찾지 못했습니다.. <br>" +
                    "code: F-001 | message: 존재하지 않는 폴더입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<String> addLinkCard(LinkCardReq req);
}
