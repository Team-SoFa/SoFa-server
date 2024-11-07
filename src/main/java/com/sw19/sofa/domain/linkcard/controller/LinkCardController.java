package com.sw19.sofa.domain.linkcard.controller;

import com.sw19.sofa.domain.linkcard.api.LinkCardApi;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardInfoEditReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.CreateLinkCardBasicInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardSimpleRes;
import com.sw19.sofa.domain.linkcard.service.LinkCardMangeService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/linkCard")
public class LinkCardController implements LinkCardApi {

    private final LinkCardMangeService linkCardMangeService;

    @Override
    @PostMapping("/ai")
    public ResponseEntity<CreateLinkCardBasicInfoRes> createLinkCardBasicInfo(@AuthMember Member member,@Validated @RequestBody CreateLinkCardBasicInfoReq req) {
        CreateLinkCardBasicInfoRes res = linkCardMangeService.createLinkCardBasicInfo(member, req);
        return BaseResponse.ok(res);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LinkCardRes> getLinkCard(@PathVariable String id) {
        LinkCardRes res = linkCardMangeService.getLinkCard(id);
        return BaseResponse.ok(res);
    }

    @Override
    @PostMapping
    public ResponseEntity<String> addLinkCard(@Validated @RequestBody LinkCardReq req) {
        linkCardMangeService.addLinkCard(req);
        return BaseResponse.ok("링크 카드 추가 완료");
    }

    @Override
    @GetMapping("/list/{folderId}")
    public ResponseEntity<ListRes<LinkCardSimpleRes>> getLinkCardList(
            @PathVariable @Schema(description = "폴더 아이디") String folderId,
            @RequestParam @Schema(description = "정렬 방식", example = "RECENTLY_SAVED(최근 저장순)/RECENTLY_VIEWED(최근 조회순)/MOST_VIEWED(최다 조회순)") SortBy sortBy,
            @RequestParam @Schema(description = "정렬 순서", example = "ASCENDING(오름차순)/DESCENDING(내림차순)") SortOrder sortOrder,
            @RequestParam @Schema(description = "마지막 링크카드 아이디", example = "처음 조회시에는 0 입력") String lastId,
            @RequestParam @Schema(description = "요청 갯수") int limit
    ) {
        ListRes<LinkCardSimpleRes> res = linkCardMangeService.getLinkCardList(folderId, sortBy, sortOrder, lastId, limit);
        return BaseResponse.ok(res);
    }

    @Override
    @PatchMapping("/{id}/info")
    public ResponseEntity<LinkCardInfoRes> editLinkCardInfo(
            @PathVariable @Schema(description = "링크 카드 아이디") String id,
            @Validated @RequestBody LinkCardInfoEditReq req
    ) {
        LinkCardInfoRes res = linkCardMangeService.editLinkCardInfo(id, req);
        return BaseResponse.ok(res);
    }
}
