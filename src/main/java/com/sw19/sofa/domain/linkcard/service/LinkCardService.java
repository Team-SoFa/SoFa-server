package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardSimpleRes;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.error.LinkCardErrorCode;
import com.sw19.sofa.domain.linkcard.repository.LinkCardRepository;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
                .memo(req.memo())
                .views(0L)
                .visitedAt(LocalDateTime.now())
                .build();

        return linkCardRepository.save(linkCard);
    }



    @Transactional(readOnly = true)
    public ListRes<LinkCardSimpleRes> getLinkCardSimpleResListByFolderIdAndSortCondition(Long folderId,  SortBy sortBy, SortOrder sortOrder, int limit, Long lastId){
        List<LinkCard> linkCardList = linkCardRepository.findAllByFolderIdAndSortCondition(folderId, sortBy, sortOrder, limit, lastId);
        boolean hasNext = false;

        if(linkCardList.size() > limit){
            hasNext = true;
            linkCardList.remove(limit);
        }

        List<LinkCardSimpleRes> linkCardSimpleResList = linkCardList.stream().map(LinkCardSimpleRes::new).toList();

        return new ListRes<>(
                linkCardSimpleResList,
                limit,
                linkCardSimpleResList.size(),
                hasNext
        );

    }
}
