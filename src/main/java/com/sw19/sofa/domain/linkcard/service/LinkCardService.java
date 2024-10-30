package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.error.LinkCardErrorCode;
import com.sw19.sofa.domain.linkcard.repository.LinkCardRepository;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LinkCardService {
    private final LinkCardRepository linkCardRepository;

    @Transactional(readOnly = true)
    public LinkCardDto getLinkCard(Long id){
        LinkCard linkCard = linkCardRepository.findById(id).orElseThrow(() -> new BusinessException(LinkCardErrorCode.NOT_FOUND_LINK_CARD));
        return new LinkCardDto(linkCard);
    }

    @Transactional
    public LinkCard addLinkCard(LinkCardReq req, Folder folder, Article article) {
        LinkCard linkCard = LinkCard.builder()
                .article(article)
                .folder(folder)
                .title(req.title())
                .isShare(false)
                .isAlarm(req.isAlarm())
                .memo(req.memo())
                .visitedAt(LocalDateTime.now())
                .build();

        return linkCardRepository.save(linkCard);
    }
}
