package com.sw19.sofa.domain.tag.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.api.TagApi;
import com.sw19.sofa.domain.tag.dto.request.CustomTagReq;
import com.sw19.sofa.domain.tag.dto.request.TagReq;
import com.sw19.sofa.domain.tag.dto.response.CustomTagRes;
import com.sw19.sofa.domain.tag.dto.response.TagRes;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController implements TagApi {
    private final TagService tagService;
    private final CustomTagService customTagService;

    @Override
    @GetMapping("/custom")
    public ResponseEntity<List<CustomTagRes>> getAllCustomTags(@RequestParam Member member) {
        List<CustomTag> customTags = customTagService.getAllCustomTagsByMember(member);
        return ResponseEntity.ok(customTags.stream()
                .map(CustomTagRes::new)
                .toList());
    }

    @Override
    @PostMapping("/custom")
    public ResponseEntity<CustomTagRes> addCustomTag(@RequestParam Member member, @RequestBody CustomTagReq req) {
        CustomTag customTag = customTagService.createCustomTag(member, req.getName());
        return ResponseEntity.ok(new CustomTagRes(customTag));
    }

    @Override
    @DeleteMapping("/custom/{id}")
    public ResponseEntity<String> deleteCustomTag(@PathVariable String id) {
        customTagService.deleteCustomTag(EncryptionUtil.decrypt(id));
        return ResponseEntity.ok("Custom tag deleted successfully");
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TagRes>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags.stream()
                .map(TagRes::new)
                .toList());
    }

    @Override
    @PostMapping
    public ResponseEntity<TagRes> addTag(@RequestBody TagReq req) {
        Tag tag = tagService.getOrCreateTag(req.getName(), req.getType());
        return ResponseEntity.ok(new TagRes(tag));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable String id) {
        tagService.deleteTag(EncryptionUtil.decrypt(id));
        return ResponseEntity.ok("Tag deleted successfully");
    }
}