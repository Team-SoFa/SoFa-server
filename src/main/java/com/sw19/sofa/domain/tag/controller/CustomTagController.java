package com.sw19.sofa.domain.tag.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.api.CustomTagApi;
import com.sw19.sofa.domain.tag.dto.request.CustomTagReq;
import com.sw19.sofa.domain.tag.dto.response.CustomTagRes;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import com.sw19.sofa.global.util.EncryptionUtil;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custom-tags")
@RequiredArgsConstructor
public class CustomTagController implements CustomTagApi{
    private final CustomTagService customTagService;

    @GetMapping
    public ResponseEntity<List<CustomTagRes>> getAllCustomTags(@AuthMember Member member) {
        return ResponseEntity.ok(customTagService.getAllCustomTagsByMember(member).stream()
                .map(CustomTagRes::new)
                .toList());
    }

    @PostMapping
    public ResponseEntity<CustomTagRes> createCustomTag(
            @AuthMember Member member,
            @RequestBody CustomTagReq req) {
        return ResponseEntity.ok(new CustomTagRes(
                customTagService.createCustomTag(member, req.getName())));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CustomTagRes> updateCustomTag(
            @AuthMember Member member,
            @PathVariable String id,
            @RequestBody CustomTagReq req
    ) {
        customTagService.updateCustomTag(
                EncryptionUtil.decrypt(id),
                req.getName(),
                member
        );
        CustomTag updatedTag = customTagService.getCustomTag(EncryptionUtil.decrypt(id));
        return ResponseEntity.ok(new CustomTagRes(updatedTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomTag(
            @AuthMember Member member,
            @PathVariable String id) {
        customTagService.deleteCustomTag(EncryptionUtil.decrypt(id), member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomTagRes>> searchCustomTags(
            @RequestParam String keyword) {
        return ResponseEntity.ok(customTagService.searchCustomTagsByKeyword(keyword).stream()
                .map(CustomTagRes::new)
                .toList());
    }
}