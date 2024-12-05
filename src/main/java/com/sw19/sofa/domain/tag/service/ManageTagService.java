package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageTagService {
    private final TagService tagService;
    private final CustomTagService customTagService;

    public List<TagSearchRes> getAllTags(Member member) {
        List<TagSearchRes> results = new ArrayList<>();

        results.addAll(tagService.getAllTags().stream()
                .map(TagSearchRes::fromTag)
                .toList());

        results.addAll(customTagService.getAllCustomTagsByMember(member).stream()
                .map(TagSearchRes::fromCustomTagRes)
                .toList());

        return results;
    }
}