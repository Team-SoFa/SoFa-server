package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.repository.LinkCardTagRepository;
import com.sw19.sofa.domain.member.entity.MemberTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkCardTagService {
    private final LinkCardTagRepository linkCardTagRepository;

    @Transactional
    public List<LinkCardTag> addLinkCardTag(LinkCard linkCard, List<MemberTag> memberTagList){
        List<LinkCardTag> linkCardTagList = memberTagList.stream().map(
                memberTag -> LinkCardTag.builder()
                        .linkCard(linkCard)
                        .memberTag(memberTag)
                        .build()
        ).toList();

         return linkCardTagRepository.saveAll(linkCardTagList);
    }
}
