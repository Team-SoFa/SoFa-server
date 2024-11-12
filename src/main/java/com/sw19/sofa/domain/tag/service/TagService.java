package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getTagList(List<String> tagNameList){
        return tagRepository.findAllByNameIn(tagNameList);
    }

    public List<TagDto> getTagDtoListByIdList(List<Long> tagIdList){
        return tagRepository.findAllByIdIn(tagIdList).stream()
                .map(TagDto::new)
                .toList();
    }
}
