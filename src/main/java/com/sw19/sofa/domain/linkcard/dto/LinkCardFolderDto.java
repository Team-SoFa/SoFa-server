package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.global.common.dto.FolderDto;
import com.sw19.sofa.global.util.EncryptionUtil;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardFolderDto(
        @Schema(description = "폴더 아이디") String id,
        @Schema(description = "폴더명") String name
) {
    public LinkCardFolderDto(FolderDto folderDto){
        this(EncryptionUtil.encrypt(folderDto.id()), folderDto.name());
    }
    public LinkCardFolderDto(Folder folder){
        this(folder.getEncryptId(), folder.getName());
    }
}
