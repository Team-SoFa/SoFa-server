package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.error.LinkCardErrorCode;
import com.sw19.sofa.domain.linkcard.repository.LinkCardRepository;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkCardService {
    private final LinkCardRepository linkCardRepository;

    public LinkCardDto getLinkCard(Long id){
        LinkCard linkCard = linkCardRepository.findById(id).orElseThrow(() -> new BusinessException(LinkCardErrorCode.NOT_FOUND_LINK_CARD));
        return new LinkCardDto(linkCard);
    }
}
