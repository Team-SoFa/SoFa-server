package com.sw19.sofa.domain.recycleBin.service;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.linkcard.service.LinkCardTagService;
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
@Transactional(readOnly = true)
public class RecycleBinManageService {
    private final LinkCardService linkCardService;
    private final FolderService folderService;
    private final LinkCardTagService linkCardTagService;

    @Transactional
    public void permanentlyDelete(String encryptId) {
        Long id = EncryptionUtil.decrypt(encryptId);
        LinkCard linkCard = linkCardService.getLinkCard(id);

        linkCardTagService.deleteAllByLinkCard(linkCard);
        linkCardService.deleteLinkCard(linkCard);
    }

    public ListRes<RecycleBinLinkCardRes> getLinkCardListInRecycleBin(Member member, RecycleBinSortBy recycleBinSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long lastId = EncryptionUtil.decrypt(encryptLastId);

        Folder recycleBin = folderService.getFolderByNameAndMemberOrElseThrow(Constants.recycleBinName, member);
        ListRes<LinkCard> linkCardListRes = linkCardService.getLinkCardSimpleResListByFolderIdAndSortCondition(List.of(recycleBin.getId()), recycleBinSortBy, sortOrder, limit, lastId);
        List<RecycleBinLinkCardRes> recycleBinLinkCardResList = linkCardListRes.data().stream().map(RecycleBinLinkCardRes::new).toList();

        return new ListRes<>(
                recycleBinLinkCardResList,
                linkCardListRes.limit(),
                linkCardListRes.size(),
                linkCardListRes.hasNext()
        );
    }

    @Transactional
    public void recycle(String encryptLinkCardId, String encryptFolderId) {
        Long linkCardId = EncryptionUtil.decrypt(encryptLinkCardId);
        Long folderId = EncryptionUtil.decrypt(encryptFolderId);

        Folder folder = folderService.getFolder(folderId);
        linkCardService.editLinkCardFolder(linkCardId, folder);
    }

    @Transactional
    public void deleteExpiredLinkCardList() {
        List<LinkCard> linkCardList = linkCardService.getExpiredLinkCardsInRecycleBin();
        linkCardService.deleteLinkCardList(linkCardList);
    }

    @Transactional
    public void addRecycleBin(Member member){
        folderService.addFolder(member, Constants.recycleBinName);
    }
}
