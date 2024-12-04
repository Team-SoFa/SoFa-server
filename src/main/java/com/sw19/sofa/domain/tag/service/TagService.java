package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.dto.response.TagRes;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.error.TagErrorCode;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    public List<TagRes> searchTagsByKeyword(String keyword) {  // Entity 대신 DTO 반환
        return tagRepository.findAllByNameContainingIgnoreCase(keyword).stream()
                .map(TagRes::new)
                .toList();
    }

    public List<TagDto> getTagDtoListByIdList(List<Long> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return List.of();
        }
        return tagRepository.findAllByIdIn(tagIdList).stream()
                .map(TagDto::new)
                .toList();
    }

    @Transactional
    public void deleteTag(String encryptedId) {
        Long id = EncryptionUtil.decrypt(encryptedId);
        if (!tagRepository.existsById(id)) {
            throw new BusinessException(TagErrorCode.TAG_NOT_FOUND);
        }
        tagRepository.deleteById(id);
    }

    public List<Tag> getTagList(List<String> tagNameList) {
        if (tagNameList == null || tagNameList.isEmpty()) {
            return List.of();
        }
        return tagRepository.findAllByNameIn(tagNameList);
    }
}