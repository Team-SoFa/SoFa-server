package com.sw19.sofa.domain.searchbox.api;

import com.sw19.sofa.domain.searchbox.dto.request.SearchBoxReq;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.global.common.dto.ListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "🔍 Search")
public interface SearchBoxApi {
    @Operation(summary = "통합 검색", description = "폴더, 태그, 키워드로 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과가 성공적으로 반환되었습니다.")
    })
    ResponseEntity<ListRes<SearchBoxRes>> search(
            SearchBoxReq req,
            @RequestParam String lastId,
            @RequestParam int limit
    );
}