package com.sw19.sofa.domain.searchbox.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "🔍 Search")
public interface SearchBoxApi {
    @Operation(summary = "통합 검색", description = "폴더, 태그, 키워드를 조합하여 링크카드를 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 리스트"),
            @ApiResponse(
                    responseCode = "403",
                    description = "폴더 접근 권한 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ListRes<SearchBoxRes>> search(
            @RequestParam(required = false) @Parameter(description = "폴더 ID") String folderId,
            @RequestParam(required = false) @Parameter(description = "태그 ID 목록") List<String> tagIds,
            @RequestParam(required = false) @Parameter(description = "검색 키워드") String keyword,
            @AuthMember Member member,
            @RequestParam(defaultValue = "0") @Parameter(description = "마지막 조회 ID") String lastId,
            @RequestParam(defaultValue = "20") @Parameter(description = "조회 개수") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") @Parameter(description = "정렬 기준") SearchBoxSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") @Parameter(description = "정렬 방향") SortOrder sortOrder
    );

    @Operation(summary = "최근 검색 키워드 조회", description = "사용자의 최근 검색 키워드 목록을 조회합니다")
    ResponseEntity<List<String>> getRecentSearchKeywords(@AuthMember Member member);

    @Operation(summary = "태그 검색", description = "모든 태그를 검색합니다")
    ResponseEntity<List<TagSearchRes>> searchTags(
            @RequestParam String keyword
    );

    @Operation(summary = "최근 검색 태그 조회", description = "사용자의 최근 검색 태그 목록을 조회합니다")
    ResponseEntity<List<String>> getRecentSearchTags(@AuthMember Member member);
}