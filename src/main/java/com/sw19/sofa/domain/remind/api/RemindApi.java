package com.sw19.sofa.domain.remind.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.dto.response.RemindRes;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "⏰ Remind")
public interface RemindApi {
    @Operation(summary = "리마인드 목록 조회", description = "리마인드함에 있는 링크카드 목록을 조회합니다.")
    ResponseEntity<ListRes<RemindRes>> getRemindList(
            @AuthMember Member member,
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") RemindSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    );
}