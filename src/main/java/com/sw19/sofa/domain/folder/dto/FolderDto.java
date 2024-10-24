package com.sw19.sofa.domain.folder.dto;

import com.sw19.sofa.domain.folder.entity.Folder;

public record FolderDto(String id, String name) {
    public FolderDto(Folder folder) {
        this(folder.getEncryptId(), folder.getName());
    }
}
