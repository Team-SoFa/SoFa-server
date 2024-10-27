package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagDto> getTagDtoList(List<String> tagNameList){
        return tagRepository.findAllByNameIn(tagNameList).stream()
                .map(TagDto::new)
                .toList();
    }
}
