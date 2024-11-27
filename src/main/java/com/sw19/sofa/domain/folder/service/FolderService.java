package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.dto.request.FolderReq;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.repository.FolderRepository;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sw19.sofa.domain.folder.error.FolderErrorCode.*;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public FolderListRes getFolderList(Member member) {
        List<FolderRes> folderResList = folderRepository.findAllByMember(member).stream().map(FolderRes::new).toList();
        return new FolderListRes(folderResList);
    }

    @Transactional
    public FolderListRes addFolder(Member member, FolderReq req) {
        Folder folder = Folder.builder()
                .name(req.name())
                .member(member)  //추가함 멤버 받아야해서
                .build();
        folderRepository.save(folder);
        return getFolderList(member);
    }

    @Transactional
    public void delFolder(String decryptId) {
        Folder folder = findFolder(decryptId);
        folderRepository.delete(folder);
    }

    @Transactional
    public FolderRes editFolder(String decryptId, FolderReq req) {
        Folder folder = findFolder(decryptId);
        folder.edit(req.name());
        Folder save = folderRepository.save(folder);
        return new FolderRes(save);
    }

    @Transactional(readOnly = true)
    public Folder findFolder(String decryptId){
        Long id = EncryptionUtil.decrypt(decryptId);
        return folderRepository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND_FOLDER));
    }
}
