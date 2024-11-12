package com.sw19.sofa.domain.recycleBin.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.recycleBin.api.RecycleBinApi;
import com.sw19.sofa.domain.recycleBin.dto.enums.RecycleBinSortBy;
import com.sw19.sofa.domain.recycleBin.dto.request.RecycleReq;
import com.sw19.sofa.domain.recycleBin.dto.response.RecycleBinLinkCardRes;
import com.sw19.sofa.domain.recycleBin.service.RecycleBinManageService;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recycleBin")
public class RecycleBinController implements RecycleBinApi {
    private final RecycleBinManageService recycleBinManageService;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> permanentlyDelete(@PathVariable String id) {
        recycleBinManageService.permanentlyDelete(id);
        return BaseResponse.ok("링크 카드 영구 삭제 완료");
    }

    @Override
    @PostMapping("/{id}")
    public ResponseEntity<String> recycle(@PathVariable String id,@RequestBody @Validated RecycleReq req) {
        recycleBinManageService.recycle(id, req.folderId());
        return BaseResponse.ok("링크 카드 복원 완료");
    }

    @Override
    @GetMapping
    public ResponseEntity<ListRes<RecycleBinLinkCardRes>> getLinkCardListInRecycleBin(
            @AuthMember Member member, @RequestParam(name = "sortBy") RecycleBinSortBy recycleBinSortBy, @RequestParam SortOrder sortOrder, @RequestParam String lastId, @RequestParam int limit
    ) {
        ListRes<RecycleBinLinkCardRes> res = recycleBinManageService.getLinkCardListInRecycleBin(member, recycleBinSortBy, sortOrder, lastId, limit);
        return BaseResponse.ok(res);
    }
}
