package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.dto.request.FolderReq;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.constants.Constants;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderMangeService {
    private final FolderService folderService;
    private final LinkCardService linkCardService;

    @Transactional(readOnly = true)
    public FolderListRes getFolderList(Member member) {


        List<FolderRes> folderRes = folderService.getFolderList(member).folderList();

        Optional<FolderRes> minEncryptedIdFolder = folderRes.stream()
                .filter(f -> "휴지통".equals(f.name()))
                .min(Comparator.comparingLong(FolderRes::encryptionId));

        List<FolderRes> ret = folderRes.stream()
                .filter(f -> minEncryptedIdFolder.isEmpty() || !minEncryptedIdFolder.get().id().equals(f.id()))
                .toList();

        return new FolderListRes(ret);
    }

    @Transactional
    public FolderListRes addFolder(Member member, FolderReq req) {
        folderService.addFolder(member, req.name());
        return folderService.getFolderList(member);
    }

    @Transactional
    public void delFolder(String encryptId, Member member) {
        Long id = EncryptionUtil.decrypt(encryptId);
        Folder folder = folderService.getFolder(id);
        Folder recycleBinFolder = folderService.getFolderByNameAndMemberOrElseThrow(Constants.recycleBinName, member);
        linkCardService.editLinkCardListInSrcFolderByDstFolder(folder,recycleBinFolder);

        folderService.delFolder(folder);
    }

    @Transactional
    public FolderRes editFolder(String encryptId, FolderReq req) {
        Long id = EncryptionUtil.decrypt(encryptId);

        Folder folder = folderService.getFolder(id);
        return folderService.editFolder(folder, req.name());
    }
}
