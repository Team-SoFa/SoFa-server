package com.sw19.sofa.domain.tag.controller;

import com.sw19.sofa.domain.tag.api.TagApi;
import com.sw19.sofa.domain.tag.dto.response.TagRes;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController implements TagApi {
    private final TagService tagService;

    @GetMapping("/search")
    public ResponseEntity<List<TagRes>> searchTags(@RequestParam String keyword) {
        return ResponseEntity.ok(tagService.searchTagsByKeyword(keyword).stream()
                .map(TagRes::new)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable String id) {
        tagService.deleteTag(EncryptionUtil.decrypt(id));
        return ResponseEntity.ok().build();
    }
}