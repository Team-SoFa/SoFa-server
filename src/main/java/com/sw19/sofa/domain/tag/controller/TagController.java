package com.sw19.sofa.domain.tag.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.api.TagApi;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import com.sw19.sofa.domain.tag.service.ManageTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController implements TagApi {
    private final TagService tagService;
    private final ManageTagService manageTagService;
    @Override
    @GetMapping
    public ResponseEntity<List<TagSearchRes>> getAllTags(@AuthMember Member member) {
        return ResponseEntity.ok(manageTagService.getAllTags(member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable String id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}