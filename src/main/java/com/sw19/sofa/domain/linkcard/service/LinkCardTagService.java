package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.article.service.ArticleTagService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.linkcard.repository.LinkCardTagRepository;
import com.sw19.sofa.global.common.dto.ArticleTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkCardTagService {
    private final LinkCardTagRepository linkCardTagRepository;
    private final ArticleTagService articleTagService;

    @Transactional
    public List<LinkCardTag> addLinkCardTagList(LinkCard linkCard, List<LinkCardTagSimpleDto> tagDtoList) {
        List<LinkCardTag> linkCardTagList = tagDtoList.stream()
                .map(tagDto -> LinkCardTag.builder()
                        .linkCard(linkCard)
                        .tagId(tagDto.id())
                        .tagType(tagDto.tagType())
                        .build()
                ).toList();

        return linkCardTagRepository.saveAll(linkCardTagList);
    }

    @Transactional(readOnly = true)
    public List<LinkCardTagSimpleDto> getLinkCardTagSimpleDtoListByLinkCardId(Long linkCardId) {
        return linkCardTagRepository.findAllByLinkCardId(linkCardId)
                .stream()
                .map(LinkCardTagSimpleDto::new)
                .toList();
    }

    @Transactional
    public void deleteLinkCardTag(Long linkCardId, Long tagId, TagType tagType) {
        linkCardTagRepository.deleteByLinkCard_IdAndTagIdAndTagType(linkCardId, tagId, tagType);
    }

    @Transactional
    public void deleteAllByLinkCard(LinkCard linkCard){
        linkCardTagRepository.deleteAllByLinkCard(linkCard);
    }
}