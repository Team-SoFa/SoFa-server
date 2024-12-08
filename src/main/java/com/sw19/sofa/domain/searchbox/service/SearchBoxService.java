package com.sw19.sofa.domain.searchbox.service;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardTagService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.domain.searchbox.repository.SearchBoxRepository;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.repository.CustomTagRepository;
import com.sw19.sofa.domain.tag.repository.TagRepository;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.error.code.CommonErrorCode;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SearchBoxService {
    private final SearchBoxRepository searchBoxRepository;
    private final LinkCardTagService linkCardTagService;
    private final SearchHistoryService searchHistoryService;
    private final FolderService folderService;
    private final TagRepository tagRepository;
    private final CustomTagRepository customTagRepository;

    public ListRes<SearchBoxRes> search(
            String encryptedFolderId,
            List<String> encryptedTagIds,
            String keyword,
            Member member,
            String lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        if (StringUtils.hasText(keyword)) {
            searchHistoryService.addSearchKeywordHistory(member.getId(), keyword);
        }

        Long lastIdLong = lastId == null || "0".equals(lastId) ? null : EncryptionUtil.decrypt(lastId);
        Long folderId = encryptedFolderId == null || encryptedFolderId.equals("null") ? null : EncryptionUtil.decrypt(encryptedFolderId);
        keyword = keyword == null || keyword.equals("null") ? null : keyword;
        List<Long> tagIdList  = encryptedTagIds == null || encryptedTagIds.isEmpty() || encryptedTagIds.contains("null") ? null :encryptedTagIds.stream().map(EncryptionUtil::decrypt).toList();

        List<LinkCard> linkCards;

        if (folderId != null && tagIdList != null) {
            validateFolderAccess(folderService.findFolder(folderId), member);

            linkCards = searchBoxRepository.searchByTagsAndFolder(
                    tagIdList,
                    folderId,
                    keyword,
                    lastIdLong,
                    limit,
                    sortBy,
                    sortOrder
            );
        } else if (folderId != null) {
            validateFolderAccess(folderService.findFolder(folderId), member);

            linkCards = searchBoxRepository.searchByFolder(
                    folderId,
                    keyword,
                    lastIdLong,
                    limit,
                    sortBy,
                    sortOrder
            );
        } else if (tagIdList != null && !tagIdList.isEmpty()) {
            linkCards = searchBoxRepository.searchByTags(
                    tagIdList,
                    keyword,
                    lastIdLong,
                    limit,
                    sortBy,
                    sortOrder
            );
        } else {
            linkCards = searchBoxRepository.searchAll(
                    keyword,
                    lastIdLong,
                    limit,
                    sortBy,
                    sortOrder
            );
        }

        return processSearchResults(linkCards, limit);
    }

    private ListRes<SearchBoxRes> processSearchResults(List<LinkCard> linkCards, int limit) {
        boolean hasNext = linkCards.size() > limit;
        if (hasNext) {
            linkCards = linkCards.subList(0, limit);
        }

        List<SearchBoxRes> searchResults = linkCards.stream()
                .map(linkCard -> {
                    List<LinkCardTagSimpleDto> cardTags = linkCardTagService
                            .getLinkCardTagSimpleDtoListByLinkCardId(linkCard.getId());

                    List<TagDto> tagDtoList = cardTags.stream()
                            .map(cardTag -> new TagDto(
                                    cardTag.id(),
                                    getTagName(cardTag.id())
                            ))
                            .toList();

                    return new SearchBoxRes(linkCard, tagDtoList);
                })
                .toList();

        return new ListRes<>(searchResults, limit, searchResults.size(), hasNext);
    }

    private void validateFolderAccess(Folder folder, Member member) {
        if (!folder.getMember().getId().equals(member.getId())) {
            throw new BusinessException(CommonErrorCode.FORBIDDEN);
        }
    }

    private String getTagName(Long id) {
        return tagRepository.findById(id)
                .map(Tag::getName)
                .orElseGet(() -> customTagRepository.findById(id)
                        .map(CustomTag::getName)
                        .orElse(null));
    }

    public List<TagSearchRes> searchTags(String keyword) {
        List<TagSearchRes> results = new ArrayList<>();

        results.addAll(tagRepository.findAllByNameContainingIgnoreCase(keyword).stream()
                .map(TagSearchRes::new)
                .toList());

        results.addAll(customTagRepository.findAllByNameContainingIgnoreCase(keyword).stream()
                .map(TagSearchRes::new)
                .toList());

        return results;
    }
}