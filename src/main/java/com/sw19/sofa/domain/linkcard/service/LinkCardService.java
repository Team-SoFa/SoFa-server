package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardInfoRes;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.repository.LinkCardRepository;
import com.sw19.sofa.global.common.constants.Constants;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkCardService {
    private final LinkCardRepository linkCardRepository;

    public LinkCard getLinkCardDto(Long id){
        LinkCard linkCard = linkCardRepository.findByIdOrElseThrowException(id);
        linkCard.view();
        return linkCard;
    }

    public LinkCard getLinkCard(Long id){
        return linkCardRepository.findByIdOrElseThrowException(id);
    }

    public ListRes<LinkCard> getLinkCardSimpleResListByFolderIdAndSortCondition(List<Long> folderIdList, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId){
        List<LinkCard> linkCardList = linkCardRepository.findAllByFolderIdAndSortCondition(folderIdList, sortBy, sortOrder, limit, lastId);

        boolean hasNext = false;

        if(linkCardList.size() > limit){
            hasNext = true;
            linkCardList.remove(limit);
        }

        return new ListRes<>(
                linkCardList,
                limit,
                linkCardList.size(),
                hasNext
        );

    }

    public ListRes<LinkCard> getLinkCardSimpleResListByLinkCardTagAndSortCondition(LinkCardTag linkCardTag, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId){
        List<LinkCard> linkCardList = linkCardRepository.findAllByLinkCardTagAndSortCondition(linkCardTag, sortBy, sortOrder, limit, lastId);

        boolean hasNext = false;

        if(linkCardList.size() > limit){
            hasNext = true;
            linkCardList.remove(limit);
        }

        return new ListRes<>(
                linkCardList,
                limit,
                linkCardList.size(),
                hasNext
        );

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
    public void editLinkCardListInSrcFolderByDstFolder(Folder srcFolder, Folder dstFolder){
        List<LinkCard> linkCardList = linkCardRepository.findAllByFolder(srcFolder);
        linkCardList.forEach(linkCard -> linkCard.editFolder(dstFolder));
        linkCardRepository.saveAll(linkCardList);
    }

    @Transactional
    public void enterLinkCard(LinkCard linkCard) {
        linkCard.enter();
        linkCardRepository.save(linkCard);
    }

    @Transactional
    public void deleteLinkCard(LinkCard linkCard){
        linkCardRepository.delete(linkCard);
    }

    @Transactional
    public void deleteLinkCardList(List<LinkCard> linkCardList) {linkCardRepository.deleteAll(linkCardList);}

    public List<LinkCard> getUnusedLinkCardList(){
        return linkCardRepository.findUnusedLinkCardList(getThirtyDaysAgoDateTime());
    }

    public List<LinkCard> getExpiredLinkCardsInRecycleBin(){
        return linkCardRepository.findExpiredLinkCardListInRecycleBin(getThirtyDaysAgoDateTime(), Constants.recycleBinName);
    }

    private LocalDateTime getThirtyDaysAgoDateTime() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return thirtyDaysAgo.toLocalDate().atStartOfDay();
    }
}
