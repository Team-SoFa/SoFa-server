package com.sw19.sofa.domain.linkcard.dto;

import com.sw19.sofa.global.common.dto.FolderDto;
import com.sw19.sofa.global.util.EncryptionUtil;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardFolderDto(
        @Schema(name = "태그 아이디") String id,
        @Schema(name = "태그명") String name) {
    public LinkCardFolderDto(FolderDto folderDto){
        this(EncryptionUtil.encrypt(folderDto.id()), folderDto.name());
    }
}
