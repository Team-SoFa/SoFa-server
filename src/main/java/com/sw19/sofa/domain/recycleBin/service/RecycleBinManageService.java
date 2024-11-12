package com.sw19.sofa.domain.recycleBin.service;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.recycleBin.dto.enums.RecycleBinSortBy;
import com.sw19.sofa.domain.recycleBin.dto.response.RecycleBinLinkCardRes;
import com.sw19.sofa.global.common.constants.Constants;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecycleBinManageService {
    private final LinkCardService linkCardService;
    private final FolderService folderService;
    @Transactional
    public void permanentlyDelete(String encryptId) {
        Long id = EncryptionUtil.decrypt(encryptId);
        LinkCard linkCard = linkCardService.getLinkCard(id);
        linkCardService.deleteLinkCard(linkCard);
    }

    @Transactional(readOnly = true)
    public ListRes<RecycleBinLinkCardRes> getLinkCardListInRecycleBin(Member member, RecycleBinSortBy recycleBinSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long lastId = EncryptionUtil.decrypt(encryptLastId);

        Folder recycleBin = folderService.getFolderByNameAndMember(Constants.recycleBinName, member);
        ListRes<LinkCard> linkCardListRes = linkCardService.getLinkCardSimpleResListByFolderIdAndSortCondition(recycleBin.getId(), recycleBinSortBy, sortOrder, limit, lastId);
        List<RecycleBinLinkCardRes> recycleBinLinkCardResList = linkCardListRes.data().stream().map(RecycleBinLinkCardRes::new).toList();

        return new ListRes<>(
                recycleBinLinkCardResList,
                linkCardListRes.limit(),
                linkCardListRes.size(),
                linkCardListRes.hasNext()
        );
    }
}
