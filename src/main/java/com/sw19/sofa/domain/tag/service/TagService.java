package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService { //Tag 엔티티 조회/태그 ID를 기반 DTO 목록을 반환하는 기능 함.
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<Tag> getTagList(List<String> tagNameList) {
        return tagRepository.findAllByNameIn(tagNameList);
    }

    @Transactional(readOnly = true)
    public List<TagDto> getTagDtoListByIdList(List<Long> tagIdList) {
        return tagRepository.findAllByIdIn(tagIdList).stream()
                .map(TagDto::new)
                .toList();
    }
}
