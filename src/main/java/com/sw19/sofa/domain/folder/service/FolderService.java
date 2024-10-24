package com.sw19.sofa.domain.folder.service;

import com.sw19.sofa.domain.folder.dto.FolderDto;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.repository.FolderRepository;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public FolderListRes getFolderList(Member member) {
        List<FolderDto> folderDtoList = folderRepository.findAllByMember(member).stream().map(FolderDto::new).toList();
        return new FolderListRes(folderDtoList);
    }
}
