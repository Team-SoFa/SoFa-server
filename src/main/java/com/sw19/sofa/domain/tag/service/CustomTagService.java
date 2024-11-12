package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.global.common.dto.CustomTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomTagService {
    private final CustomTagRepository customTagRepository;

    public List<CustomTagDto> getCustomTagDtoListByIdList(List<Long> tagIdList){
        return customTagRepository.findAllByIdIn(tagIdList).stream()
                .map(CustomTagDto::new)
                .toList();
    }
}
