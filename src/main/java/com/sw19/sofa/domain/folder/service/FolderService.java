package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.repository.FolderRepository;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sw19.sofa.domain.folder.error.FolderErrorCode.NOT_FOUND_FOLDER;

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
    public Folder addFolder(Member member, String name) {
        Folder folder = Folder.builder()
                .member(member)
                .name(name)
                .build();
        return folderRepository.save(folder);
    }

    @Transactional
    public void delFolder(Folder folder) {
        folderRepository.delete(folder);
    }

    @Transactional
    public FolderRes editFolder(Folder folder, String name) {
        folder.edit(name);
        Folder save = folderRepository.save(folder);
        return new FolderRes(save);
    }

    @Transactional(readOnly = true)
    public Folder getFolder(Long id){
        return folderRepository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND_FOLDER));
    }

    @Transactional(readOnly = true)
    public Folder getFolderByNameAndMemberOrElseThrow(String name, Member member){
        return folderRepository.findByNameAndMember(name, member).orElseThrow(() -> new BusinessException(NOT_FOUND_FOLDER));
    }

    @Transactional(readOnly = true)
    public Folder getFolderByNameAndMemberOrNull(String name, Member member){
        return folderRepository.findByNameAndMember(name, member).orElse(null);
    }
}
