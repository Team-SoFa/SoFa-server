package com.sw19.sofa.domain.linkcard.dto.response;

import com.sw19.sofa.domain.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkCardFolderRes(

        @Schema(description = "폴더 아이디") String id,
        @Schema(description = "폴더명") String name
) {
    public LinkCardFolderRes(Folder folder) {
        this(folder.getEncryptId(), folder.getName());
    }
}
