package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.repository.FolderTagRepository;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.FolderWithTagListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderTagService {
    private final FolderTagRepository folderTagRepository;

    @Transactional(readOnly = true)
    public List<FolderWithTagListDto> getFolderListWithTagListByFolderIdList(Member member){
        return folderTagRepository.findFolderWithTagListByMember(member);
    }
}
