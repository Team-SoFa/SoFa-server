package com.sw19.sofa.domain.linkcard.api;

import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardInfoEditReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardTagListReq;
import com.sw19.sofa.domain.linkcard.dto.response.*;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
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
    ResponseEntity<LinkCardRes> getLinkCard(@Schema(description = "링크 카드 아이디") String id);

    @Operation(summary = "링크 카드 추가", description = "링크 카드를 추가합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 추가 완료"),
            @ApiResponse(responseCode = "404", description = "code: AR-001 | message: 기본 정보를 찾지 못했습니다.. <br>" +
                    "code: F-001 | message: 존재하지 않는 폴더입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<String> addLinkCard(LinkCardReq req);

    @Operation(summary = "링크 카드 리스트 조회", description = "링크 카드 리스트를 조회합니다 <br>" +
            "정렬 순서 및 정렬 방식 변경 시 새로운 조회 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 간소화 리스트")
    })
    ResponseEntity<ListRes<LinkCardSimpleRes>> getLinkCardList(
            @Schema(description = "폴더 아이디") String folderId,
            @Schema(description = "정렬 방식", example = "RECENTLY_SAVED(최근 저장순)/RECENTLY_VIEWED(최근 조회순)/MOST_VIEWED(최다 조회순)") SortBy sortBy,
            @Schema(description = "정렬 순서", example = "ASCENDING(오름차순)/DESCENDING(내림차순)") SortOrder sortOrder,
            @Schema(description = "마지막 링크카드 아이디", example = "처음 조회시에는 0 입력") String lastId,
            @Schema(description = "요청 갯수") int limit
    );

    @Operation(summary = "링크 카드 타이틀, 메모, 요약 수정", description = "링크 카드의 타이틀, 메모, 요약을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정된 링크 카드 타이틀, 메모, 요약 정보")
    })
    ResponseEntity<LinkCardInfoRes> editLinkCardInfo(
            @Schema(description = "링크 카드 아이디") String id, LinkCardInfoEditReq req
    );

    @Operation(summary = "링크 카드 태그 추가", description = "링크 카드에 태그를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추가된 태그 정보 리스트")
    })
    ResponseEntity<LinkCardTagListRes> addLinkCardTag(
            @Schema(description = "링크 카드 아이디") String id, LinkCardTagListReq req
    );
}
