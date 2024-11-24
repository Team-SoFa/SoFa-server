package com.sw19.sofa.domain.searchbox.service;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardTagService;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.domain.searchbox.repository.SearchBoxRepository;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.common.enums.SortOrder;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchBoxService {
    private final SearchBoxRepository searchBoxRepository;
    private final TagService tagService;
    private final LinkCardTagService linkCardTagService;

    public ListRes<SearchBoxRes> searchByFolder(
            String encryptedFolderId,
            String lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        Long folderId = EncryptionUtil.decrypt(encryptedFolderId);
        Long lastIdLong = "0".equals(lastId) ? 0L : EncryptionUtil.decrypt(lastId);

        List<LinkCard> linkCards = searchBoxRepository.searchByFolder(
                folderId,
                lastIdLong,
                limit + 1,
                sortBy,
                sortOrder
        );

        return getSearchResult(linkCards, limit);
    }

    public ListRes<SearchBoxRes> searchByTags(
            List<String> encryptedTagIds,
            String lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        List<Long> tagIds = encryptedTagIds.stream()
                .map(EncryptionUtil::decrypt)
                .toList();
        Long lastIdLong = "0".equals(lastId) ? 0L : EncryptionUtil.decrypt(lastId);

        List<LinkCard> linkCards = searchBoxRepository.searchByTags(
                tagIds,
                lastIdLong,
                limit + 1,
                sortBy,
                sortOrder
        );

        return getSearchResult(linkCards, limit);
    }

    public ListRes<SearchBoxRes> searchAllLinkCards(
            String lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        Long lastIdLong = "0".equals(lastId) ? 0L : EncryptionUtil.decrypt(lastId);

        List<LinkCard> linkCards = searchBoxRepository.searchAll(
                lastIdLong,
                limit + 1,
                sortBy,
                sortOrder
        );

        return getSearchResult(linkCards, limit);
    }

    private ListRes<SearchBoxRes> getSearchResult(List<LinkCard> linkCards, int limit) {
        boolean hasNext = false;
        if (linkCards.size() > limit) {
            hasNext = true;
            linkCards = linkCards.subList(0, limit);
        }

        List<SearchBoxRes> searchResults = linkCards.stream()
                .map(linkCard -> {
                    List<LinkCardTagSimpleDto> cardTags = linkCardTagService
                            .getLinkCardTagSimpleDtoListByLinkCardId(linkCard.getId());
                    List<TagDto> tags = tagService.getTagDtoListByIdList(
                            cardTags.stream()
                                    .map(LinkCardTagSimpleDto::id)
                                    .toList()
                    );
                    return new SearchBoxRes(linkCard, tags);
                })
                .toList();

        return new ListRes<>(searchResults, limit, searchResults.size(), hasNext);
    }
}