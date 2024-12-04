package com.sw19.sofa.domain.tag.service;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.error.TagErrorCode;
import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.global.common.dto.CustomTagDto;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomTagService {
    private final CustomTagRepository customTagRepository;

    public List<CustomTag> getAllCustomTagsByMember(Member member) {
        return customTagRepository.findByMember(member);
    }

    public CustomTag getCustomTag(String encryptedId) {  // Long id -> String encryptedId로 변경
        Long id = EncryptionUtil.decrypt(encryptedId);
        return customTagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TagErrorCode.TAG_NOT_FOUND));
    }

    @Transactional
    public CustomTag createCustomTag(Member member, String name) {
        if (customTagRepository.existsByMemberAndName(member, name)) {
            throw new BusinessException(TagErrorCode.TAG_ALREADY_EXISTS);
        }
        CustomTag customTag = CustomTag.builder()
                .member(member)
                .name(name)
                .build();
        return customTagRepository.save(customTag);
    }

    @Transactional
    public void updateCustomTag(String encryptedId, String name, Member member) {  // Long id -> String encryptedId로 변경
        Long id = EncryptionUtil.decrypt(encryptedId);   // 서비스에서 복호화 처리
        CustomTag customTag = customTagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TagErrorCode.TAG_NOT_FOUND));

        if (!customTag.getMember().getId().equals(member.getId())) {
            throw new BusinessException(TagErrorCode.TAG_NOT_OWNED_BY_MEMBER);
        }

        if (!customTag.getName().equals(name) &&
                customTagRepository.existsByMemberAndName(member, name)) {
            throw new BusinessException(TagErrorCode.TAG_ALREADY_EXISTS);
        }

        customTag.updateName(name);
    }

    @Transactional
    public void deleteCustomTag(String encryptedId, Member member) {
        Long id = EncryptionUtil.decrypt(encryptedId);
        CustomTag customTag = customTagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TagErrorCode.TAG_NOT_FOUND));

        if (!customTag.getMember().getId().equals(member.getId())) {
            throw new BusinessException(TagErrorCode.TAG_NOT_OWNED_BY_MEMBER);
        }

        customTagRepository.deleteById(id);
    }

    public List<CustomTagDto> getCustomTagDtoListByIdList(List<Long> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return List.of();
        }
        return customTagRepository.findAllByIdIn(tagIdList).stream()
                .map(CustomTagDto::new)
                .toList();
    }

    public List<CustomTag> searchCustomTagsByKeyword(String keyword) {
        return customTagRepository.findAllByNameContainingIgnoreCase(keyword);
    }
}