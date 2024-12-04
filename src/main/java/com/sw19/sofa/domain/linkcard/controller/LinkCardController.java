package com.sw19.sofa.domain.linkcard.controller;

import com.sw19.sofa.domain.linkcard.api.LinkCardApi;
import com.sw19.sofa.domain.linkcard.dto.request.*;
import com.sw19.sofa.domain.linkcard.dto.response.*;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.linkcard.service.LinkCardMangeService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.domain.linkcard.dto.enums.LinkCardSortBy;
import com.sw19.sofa.global.common.enums.SortOrder;
import com.sw19.sofa.global.util.EncryptionUtil;
import com.sw19.sofa.security.jwt.AuthMember;
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
    public ResponseEntity<LinkCardRes> getLinkCard(
            @PathVariable String id,
            @AuthMember Member member
    ) {
        LinkCardRes res = linkCardMangeService.getLinkCard(id, member);
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
            @PathVariable String folderId, @RequestParam(name = "sortBy") LinkCardSortBy linkCardSortBy, @RequestParam(name = "sortOrder") SortOrder sortOrder, @RequestParam String lastId, @RequestParam int limit
    ) {
        ListRes<LinkCardSimpleRes> res = linkCardMangeService.getLinkCardList(folderId, linkCardSortBy, sortOrder, lastId, limit);
        return BaseResponse.ok(res);
    }

    @Override
    @PatchMapping("/{id}/info")
    public ResponseEntity<LinkCardInfoRes> editLinkCardInfo(
            @PathVariable String id, @Validated @RequestBody LinkCardInfoEditReq req
    ) {
        LinkCardInfoRes res = linkCardMangeService.editLinkCardInfo(id, req);
        return BaseResponse.ok(res);
    }

    @Override
    @PostMapping("/{id}/tag")
    public ResponseEntity<LinkCardTagListRes> addLinkCardTag(
            @PathVariable String id, @RequestBody LinkCardTagListReq req
    ) {
        LinkCardTagListRes res = linkCardMangeService.addLinkCardTag(id, req.tagList());
        return BaseResponse.ok(res);
    }

    @Override
    @DeleteMapping("/{id}/tag")
    public ResponseEntity<String> deleteLinkCardTag(
            @PathVariable String id, @RequestParam  String tagId, @RequestParam TagType tagType
    ) {
        linkCardMangeService.deleteLinkCardTag(id, tagId, tagType);
        return BaseResponse.ok("삭제 성공");
    }

    @Override
    @PatchMapping("/{id}/folder")
    public ResponseEntity<LinkCardFolderRes> editLinkCardFolder(
            @PathVariable String id, @RequestBody LinkCardFolderReq req
    ) {
        LinkCardFolderRes res = linkCardMangeService.editLinkCardFolder(id,req.id());
        return BaseResponse.ok(res);
    }

    @Override
    @PostMapping("/{id}/enter")
    public ResponseEntity<String> enterLinkCard(
            @PathVariable String id,
            @AuthMember Member member
    ) {
        linkCardMangeService.enterLinkCard(id, member);
        return BaseResponse.ok("링크 카드 방문 정보 반영");
    }
}
