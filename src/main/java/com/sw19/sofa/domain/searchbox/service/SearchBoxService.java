package com.sw19.sofa.domain.searchbox.service;

import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardTagService;
import com.sw19.sofa.domain.searchbox.repository.SearchBoxRepository;
import com.sw19.sofa.domain.searchbox.dto.request.SearchBoxReq;
import com.sw19.sofa.domain.searchbox.dto.response.SearchBoxRes;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.TagDto;
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

    public ListRes<SearchBoxRes> search(SearchBoxReq req, String lastId, int limit) {
        Long lastIdLong = "0".equals(lastId) ? 0L : EncryptionUtil.decrypt(lastId);
        Long folderId = req.encryptedFolderId() != null ?
                EncryptionUtil.decrypt(req.encryptedFolderId()) : null;

        List<Long> tagIds = null;
        if (req.encryptedTagIds() != null && !req.encryptedTagIds().isEmpty()) {
            tagIds = req.encryptedTagIds().stream()
                    .map(EncryptionUtil::decrypt)
                    .toList();
        }

        List<LinkCard> linkCards = searchBoxRepository.search(
                req.keyword(),
                folderId,
                tagIds,
                lastIdLong
        );

        // limit + 1개를 가져와서 다음 페이지 존재 여부 확인
        List<LinkCard> limitedCards = linkCards.stream().limit(limit + 1).toList();
        boolean hasNext = limitedCards.size() > limit;

        // 실제 반환할 결과는 limit 개수만큼
        List<SearchBoxRes> searchResults = limitedCards.stream()
                .limit(limit)
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