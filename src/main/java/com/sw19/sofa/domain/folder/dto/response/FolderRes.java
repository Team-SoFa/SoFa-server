package com.sw19.sofa.domain.folder.dto.response;

import com.sw19.sofa.domain.folder.entity.Folder;

public record FolderRes(String id, String name) {
    public FolderRes(Folder folder){
        this(folder.getEncryptId(),folder.getName());
    }
}
