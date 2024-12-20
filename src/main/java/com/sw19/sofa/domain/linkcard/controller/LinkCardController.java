package com.sw19.sofa.domain.linkcard.controller;

import com.sw19.sofa.domain.linkcard.api.LinkCardApi;
import com.sw19.sofa.domain.linkcard.dto.enums.LinkCardSortBy;
import com.sw19.sofa.domain.linkcard.dto.request.*;
import com.sw19.sofa.domain.linkcard.dto.response.*;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.linkcard.service.LinkCardMangeService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
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
    @GetMapping("/list")
    public ResponseEntity<ListRes<LinkCardSimpleRes>> getLinkCardList(
            @RequestParam(name = "sortBy") LinkCardSortBy linkCardSortBy, @RequestParam SortOrder sortOrder, @RequestParam String lastId, @RequestParam int limit, @AuthMember Member member
    ) {
        ListRes<LinkCardSimpleRes> res = linkCardMangeService.getLinkCardList(member,linkCardSortBy, sortOrder, lastId, limit);
        return BaseResponse.ok(res);
    }

    @Override
    @GetMapping("/most-tag")
    public ResponseEntity<MostTagLinkCardListRes> getMostTagLinkCardList(
            @RequestParam(name = "sortBy") LinkCardSortBy linkCardSortBy, @RequestParam SortOrder sortOrder, @RequestParam String lastId, @RequestParam int limit, @AuthMember Member member) {
        MostTagLinkCardListRes res = linkCardMangeService.getMostTagLinkCardList(member, linkCardSortBy, sortOrder, lastId, limit);
        return BaseResponse.ok(res);
    }

    @Override
    @GetMapping("/list/{folderId}")
    public ResponseEntity<ListRes<LinkCardSimpleRes>> getLinkCardListByFolder(
            @PathVariable String folderId, @RequestParam(name = "sortBy") LinkCardSortBy linkCardSortBy, @RequestParam SortOrder sortOrder, @RequestParam String lastId, @RequestParam int limit
    ) {
        ListRes<LinkCardSimpleRes> res = linkCardMangeService.getLinkCardListByFolder(folderId, linkCardSortBy, sortOrder, lastId, limit);
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

    @Override
    @PostMapping("/{id}/delete")
    public ResponseEntity<String> moveLinkCardToRecycleBin(@PathVariable String id, @AuthMember Member member) {
        linkCardMangeService.moveLinkCardToRecycleBin(member, id);
        return  BaseResponse.ok("링크 카드 휴지통 이동 성공");
    }
}
