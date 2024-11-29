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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "🔍 Search")
public interface SearchBoxApi {
    @Operation(summary = "폴더 내 검색", description = "특정 폴더 내에서 링크카드를 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 리스트"),
            @ApiResponse(
                    responseCode = "403",
                    description = "폴더 접근 권한 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByFolder(
            @PathVariable String folderId,
            @RequestParam(required = false) String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    );

    @Operation(summary = "태그 기반 검색", description = "선택한 태그들이 포함된 링크카드를 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 리스트")
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByTags(
            @RequestParam List<String> tagIds,
            @RequestParam(required = false) String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    );

    @Operation(summary = "폴더와 태그 조합 검색", description = "특정 폴더 내에서 선택한 태그들이 포함된 링크카드를 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 리스트"),
            @ApiResponse(
                    responseCode = "403",
                    description = "폴더 접근 권한 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchByTagsAndFolder(
            @RequestParam List<String> tagIds,
            @RequestParam String folderId,
            @RequestParam(required = false) String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    );

    @Operation(summary = "전체 링크카드 검색", description = "모든 링크카드를 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 리스트")
    })
    ResponseEntity<ListRes<SearchBoxRes>> searchAllLinkCards(
            @RequestParam(required = false) String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    );

    @Operation(summary = "최근 검색 키워드 조회", description = "사용자의 최근 검색 키워드 목록을 조회합니다")
    ResponseEntity<List<String>> getRecentSearchKeywords(@AuthMember Member member);

    @Operation(summary = "최근 검색 태그 조회", description = "사용자의 최근 검색 태그 목록을 조회합니다")
    ResponseEntity<List<String>> getRecentSearchTags(@AuthMember Member member);
}