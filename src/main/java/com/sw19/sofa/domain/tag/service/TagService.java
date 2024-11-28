package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.error.TagErrorCode;
import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<TagDto> getAllTagDtoList(List<String> tagNameList) {

        List<TagDto> aiTags = tagRepository.findAllByNameIn(tagNameList)
                .stream()
                .map(TagDto::new)
                .toList();

        List<TagDto> customTags = customTagRepository.findAllByNameIn(tagNameList)
                .stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .toList();

        List<TagDto> allTags = new ArrayList<>();
        allTags.addAll(aiTags);
        allTags.addAll(customTags);

        return allTags;
    }

    public List<TagDto> getTagDtoListByIdList(List<Long> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return List.of();
        }

        List<TagDto> aiTags = tagRepository.findAllByIdIn(tagIdList).stream()
                .map(TagDto::new)
                .toList();

        List<TagDto> customTags = customTagRepository.findAllByIdIn(tagIdList).stream()
                .map(customTag -> new TagDto(customTag.getId(), customTag.getName()))
                .toList();

        List<TagDto> allTags = new ArrayList<>();
        allTags.addAll(aiTags);
        allTags.addAll(customTags);

        return allTags;
    }

    @Transactional
    public Tag getOrCreateTag(String name, TagType type) {
        return tagRepository.findByName(name)
                .orElseGet(() -> createTag(name, type));
    }

    @Transactional
    protected Tag createTag(String name, TagType type) {
        if (tagRepository.existsByName(name)) {
            throw new BusinessException(TagErrorCode.TAG_ALREADY_EXISTS);
        }
        Tag tag = Tag.builder()
                .name(name)
                .type(type)
                .build();
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new BusinessException(TagErrorCode.TAG_NOT_FOUND);
        }
        tagRepository.deleteAllLinkCardTagsByTagId(id);
        tagRepository.deleteById(id);
    }

    public List<TagDto> searchTagsByKeyword(String keyword) {

        List<TagDto> aiTags = tagRepository.findAllByNameContainingIgnoreCase(keyword)
                .stream()
                .map(TagDto::new)
                .toList();

        List<TagDto> customTags = customTagRepository.findAllByNameContainingIgnoreCase(keyword)
                .stream()
                .map(customTag -> new TagDto(customTag.getId(), customTag.getName()))
                .toList();

        List<TagDto> allTags = new ArrayList<>();
        allTags.addAll(aiTags);
        allTags.addAll(customTags);

        return allTags;
    }
}