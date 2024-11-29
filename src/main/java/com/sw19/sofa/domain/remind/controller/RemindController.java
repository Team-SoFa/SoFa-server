package com.sw19.sofa.domain.remind.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.api.RemindApi;
import com.sw19.sofa.domain.remind.dto.response.RemindResponse;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.domain.remind.service.RemindManageService;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.enums.SortOrder;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remind")
@RequiredArgsConstructor
public class RemindController implements RemindApi {
    private final RemindManageService remindManageService;

    @Override
    @GetMapping
    public ResponseEntity<ListRes<RemindResponse>> getRemindList(
            @AuthMember Member member,
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "RECENTLY_MODIFIED") RemindSortBy sortBy,
            @RequestParam(defaultValue = "DESCENDING") SortOrder sortOrder
    ) {
        return ResponseEntity.ok(
                remindManageService.getRemindList(member, lastId, limit, sortBy, sortOrder)
        );
    }
}