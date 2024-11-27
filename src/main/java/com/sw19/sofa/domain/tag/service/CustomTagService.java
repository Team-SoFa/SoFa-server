package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.global.common.dto.CustomTagDto;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomTagService {
    private final CustomTagRepository customTagRepository;

    @Transactional(readOnly = true)
    public List<CustomTag> getAllCustomTagsByMember(Member member) {
        return customTagRepository.findByMember(member);
    }

    @Transactional
    public CustomTag createCustomTag(Member member, String name) {
        CustomTag customTag = CustomTag.builder()
                .member(member)
                .name(name)
                .build();
        return customTagRepository.save(customTag);
    }

    @Transactional
    public void deleteCustomTag(Long id) {
        customTagRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CustomTagDto> getCustomTagDtoListByIdList(List<Long> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return List.of();
        }
        return customTagRepository.findAllByIdIn(tagIdList).stream()
                .map(CustomTagDto::new)
                .toList();
    }
}