package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.dto.request.FolderReq;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolderMangeService {
    private final FolderService folderService;

    @Transactional(readOnly = true)
    public FolderListRes getFolderList(Member member) {
        return folderService.getFolderList(member);
    }

    @Transactional
    public FolderListRes addFolder(Member member, FolderReq req) {
        folderService.addFolder(req.name());
        return folderService.getFolderList(member);
    }

    @Transactional
    public void delFolder(String encryptId) {
        Long id = EncryptionUtil.decrypt(encryptId);
        Folder folder = folderService.findFolder(id);
        folderService.delFolder(folder);
    }

    @Transactional
    public FolderRes editFolder(String encryptId, FolderReq req) {
        Long id = EncryptionUtil.decrypt(encryptId);

        Folder folder = folderService.findFolder(id);
        return folderService.editFolder(folder, req.name());
    }
}
