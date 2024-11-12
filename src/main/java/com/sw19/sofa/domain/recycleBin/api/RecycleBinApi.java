package com.sw19.sofa.domain.recycleBin.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.recycleBin.dto.enums.RecycleBinSortBy;
import com.sw19.sofa.domain.recycleBin.dto.response.RecycleBinLinkCardRes;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface RecycleBinApi {
    @Operation(summary = "영구 삭제", description = "링크 카드를 영구 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 영구 삭제 성공 메세지")
    })
    ResponseEntity<String> permanentlyDelete(
            @Schema(description = "링크카드 아이디") String id
    );

    @Operation(summary = "휴지통 링크 카드 목록 조회", description = "휴지통 내의 링크 카드 목록을 조회합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "휴지통 내 링크 카드 정보(아이디, 이미지, 제목, 링크, 삭제날짜)")
    })
    ResponseEntity<ListRes<RecycleBinLinkCardRes>> getLinkCardListInRecycleBin(
            Member member,
            @Schema(description = "정렬 기준", example = "RECENTLY_DELETE(최근 삭제순)/NAME(이름순)") RecycleBinSortBy recycleBinSortBy,
            @Schema(description = "정렬 순서", example = "ASCENDING(오름차순)/DESCENDING(내림차순)") SortOrder sortOrder,
            @Schema(description = "마지막 링크카드 아이디", example = "처음 조회시에는 0 입력") String lastId,
            @Schema(description = "요청 갯수") int limit
    );
}
