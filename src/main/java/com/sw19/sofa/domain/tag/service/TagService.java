
package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;
    private final CustomTagRepository customTagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagList(List<String> tagNameList) {
        return tagRepository.findAllByNameIn(tagNameList);
    }

    public List<TagDto> getTagDtoListByIdList(List<Long> tagIdList) {
        return tagRepository.findAllByIdIn(tagIdList).stream()
                .map(TagDto::new)
                .toList();
    }

    @Transactional
    public Tag getOrCreateTag(String name, TagType type) {
        return tagRepository.findByName(name)
                .orElseGet(() -> createTag(name, type));
    }

    @Transactional
    protected Tag createTag(String name, TagType type) {
        Tag tag = Tag.builder()
                .name(name)
                .type(type)
                .build();
        return tagRepository.save(tag);
    }
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteAllLinkCardTagsByTagId(id);
        tagRepository.deleteById(id);
    }

    public List<TagDto> searchTagsByKeyword(String keyword) {
        return tagRepository.findAllByNameContainingIgnoreCase(keyword)
                .stream()
                .map(TagDto::new)
                .toList();
    }


}