package com.sw19.sofa.domain.linkcard.controller;

import com.sw19.sofa.domain.linkcard.api.LinkCardApi;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.response.CreateLinkCardBasicInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardRes;
import com.sw19.sofa.domain.linkcard.service.LinkCardMangeService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/linkCard")
public class LinkCardController implements LinkCardApi {

    private final LinkCardMangeService linkCardMangeService;

    @Override
    @PostMapping("/ai")
    public ResponseEntity<CreateLinkCardBasicInfoRes> createLinkCardBasicInfo(@AuthMember Member member, @RequestBody CreateLinkCardBasicInfoReq req) {
        CreateLinkCardBasicInfoRes res = linkCardMangeService.createLinkCardBasicInfo(member, req);
        return BaseResponse.ok(res);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LinkCardRes> getLinkCard(@PathVariable String id) {
        LinkCardRes res = linkCardMangeService.getLinkCard(id);
        return BaseResponse.ok(res);
    }
}
