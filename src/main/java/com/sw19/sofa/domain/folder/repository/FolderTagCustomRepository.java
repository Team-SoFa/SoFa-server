package com.sw19.sofa.domain.folder.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.FolderWithTagListDto;

import java.util.List;

public interface FolderTagCustomRepository {
    List<FolderWithTagListDto> findFolderWithTagListByMember(Member member);
}
