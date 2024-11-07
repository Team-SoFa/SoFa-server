package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.linkcard.repository.LinkCardTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkCardTagService {
    private final LinkCardTagRepository linkCardTagRepository;

    @Transactional
    public List<LinkCardTag> addLinkCardTagList(LinkCard linkCard, List<LinkCardTagSimpleDto> tagDtoList){
        List<LinkCardTag> linkCardTagList = tagDtoList.stream().map(
                tagDto -> LinkCardTag.builder()
                        .linkCard(linkCard)
                        .tagId(tagDto.id())
                        .tagType(tagDto.tagType())
                        .build()
        ).toList();

        return linkCardTagRepository.saveAll(linkCardTagList);
    }

    public List<LinkCardTagSimpleDto> getLinkCardTagSimpleDtoListByLinkCardId(Long linkCardId){
        return linkCardTagRepository.findAllByLinkCardId(linkCardId).stream().map(LinkCardTagSimpleDto::new).toList();
    }

    public void deleteLinkCardTag(Long linkCardId, Long tagId, TagType tagType) {
        linkCardTagRepository.deleteByLinkCardIdAndTagIdAndTagType(linkCardId, tagId, tagType);
    }
}
