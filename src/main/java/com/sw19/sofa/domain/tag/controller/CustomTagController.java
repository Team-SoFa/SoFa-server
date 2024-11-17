package com.sw19.sofa.domain.tag.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custom-tags")
@RequiredArgsConstructor
public class CustomTagController {
    private final CustomTagService customTagService;

    @GetMapping
    public ResponseEntity<List<CustomTag>> getAllCustomTags(@RequestParam Member member) {
        return ResponseEntity.ok(customTagService.getAllCustomTagsByMember(member));
    }

    @PostMapping
    public ResponseEntity<CustomTag> createCustomTag(@RequestParam Member member, @RequestParam String name) {
        return ResponseEntity.ok(customTagService.createCustomTag(member, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomTag(@PathVariable Long id) {
        customTagService.deleteCustomTag(id);
        return ResponseEntity.ok().build();
    }
}
