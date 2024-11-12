package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardSimpleRes;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.repository.LinkCardRepository;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.domain.linkcard.dto.enums.LinkCardSortBy;
import com.sw19.sofa.global.common.enums.SortOrder;
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
    public LinkCardDto getLinkCardDto(Long id){
        LinkCard linkCard = linkCardRepository.findByIdOrElseThrowException(id);
        return new LinkCardDto(linkCard);
    }

    @Transactional(readOnly = true)
    public LinkCard getLinkCard(Long id){
        return linkCardRepository.findByIdOrElseThrowException(id);
    }

    @Transactional
    public LinkCard addLinkCard(LinkCardReq req, Folder folder, Article article) {
        LinkCard linkCard = LinkCard.builder()
                .article(article)
                .folder(folder)
                .title(req.title())
                .memo(req.memo())
                .summary(req.summary())
                .views(0L)
                .visitedAt(LocalDateTime.now())
                .build();

        return linkCardRepository.save(linkCard);
    }



    @Transactional(readOnly = true)
    public ListRes<LinkCardSimpleRes> getLinkCardSimpleResListByFolderIdAndSortCondition(Long folderId, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, int limit, Long lastId){
        List<LinkCard> linkCardList = linkCardRepository.findAllByFolderIdAndSortCondition(folderId, linkCardSortBy, sortOrder, limit, lastId);
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

    @Transactional
    public LinkCardInfoRes editLinkCardInfo(Long id, String title, String memo, String summary) {
        LinkCard linkCard = linkCardRepository.findByIdOrElseThrowException(id);
        linkCard.editInfo(title,memo,summary);
        LinkCard save = linkCardRepository.save(linkCard);

        return new LinkCardInfoRes(save);
    }

    @Transactional
    public void editLinkCardFolder(Long id, Folder folder){
        LinkCard linkCard = linkCardRepository.findByIdOrElseThrowException(id);
        linkCard.editFolder(folder);
    }

    @Transactional
    public void enterLinkCard(LinkCard linkCard) {
        linkCard.enter();
        linkCardRepository.save(linkCard);
    }
}
