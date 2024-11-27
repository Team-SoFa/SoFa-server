package com.sw19.sofa.domain.searchbox.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.enums.SortOrder;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "🔍 Search")
public interface SearchBoxApi {

    @Operation(summary = "폴더별 검색", description = "특정 폴더 내의 링크카드를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 반환 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByFolder(
            @Parameter(description = "폴더 ID (암호화)") String folderId,
            @Parameter(description = "검색 키워드") String keyword,
            @Parameter(description = "로그인한 사용자") @AuthMember Member member,
            @Parameter(description = "마지막 링크카드 ID (암호화), 첫 요청시 0") String lastId,
            @Parameter(description = "한 번에 가져올 항목 수", schema = @Schema(defaultValue = "20")) int limit,
            @Parameter(description = "정렬 기준") SearchBoxSortBy sortBy,
            @Parameter(description = "정렬 방향") SortOrder sortOrder
    );

    @Operation(summary = "태그별 검색", description = "선택한 태그들이 포함된 링크카드를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 반환 성공")
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByTags(
            @Parameter(description = "태그 ID 리스트 (암호화)") List<String> tagIds,
            @Parameter(description = "검색 키워드") String keyword,
            @Parameter(description = "로그인한 사용자") @AuthMember Member member,
            @Parameter(description = "마지막 링크카드 ID (암호화), 첫 요청시 0") String lastId,
            @Parameter(description = "한 번에 가져올 항목 수", schema = @Schema(defaultValue = "20")) int limit,
            @Parameter(description = "정렬 기준") SearchBoxSortBy sortBy,
            @Parameter(description = "정렬 방향") SortOrder sortOrder
    );

    @Operation(summary = "태그와 폴더 조합 검색", description = "선택한 태그들과 폴더에 해당하는 링크카드를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 반환 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByTagsAndFolder(
            @Parameter(description = "태그 ID 리스트 (암호화)") List<String> tagIds,
            @Parameter(description = "폴더 ID (암호화)") String folderId,
            @Parameter(description = "검색 키워드") String keyword,
            @Parameter(description = "로그인한 사용자") @AuthMember Member member,
            @Parameter(description = "마지막 링크카드 ID (암호화), 첫 요청시 0") String lastId,
            @Parameter(description = "한 번에 가져올 항목 수", schema = @Schema(defaultValue = "20")) int limit,
            @Parameter(description = "정렬 기준") SearchBoxSortBy sortBy,
            @Parameter(description = "정렬 방향") SortOrder sortOrder
    );

    @Operation(summary = "전체 검색", description = "모든 링크카드를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 반환 성공")
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchAllLinkCards(
            @Parameter(description = "검색 키워드") String keyword,
            @Parameter(description = "로그인한 사용자") @AuthMember Member member,
            @Parameter(description = "마지막 링크카드 ID (암호화), 첫 요청시 0") String lastId,
            @Parameter(description = "한 번에 가져올 항목 수", schema = @Schema(defaultValue = "20")) int limit,
            @Parameter(description = "정렬 기준") SearchBoxSortBy sortBy,
            @Parameter(description = "정렬 방향") SortOrder sortOrder
    );

    @Operation(summary = "최근 검색어 조회", description = "사용자의 최근 검색어 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "최근 검색어 목록 반환 성공")
    ResponseEntity<List<String>> getRecentSearchKeywords(
            @Parameter(description = "로그인한 사용자") @AuthMember Member member
    );

    @Operation(summary = "최근 검색 태그 조회", description = "사용자의 최근 검색 태그 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "최근 검색 태그 목록 반환 성공")
    ResponseEntity<List<String>> getRecentSearchTags(
            @Parameter(description = "로그인한 사용자") @AuthMember Member member
    );
}