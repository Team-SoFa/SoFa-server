package com.sw19.sofa.domain.folder.dto.response;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.global.util.EncryptionUtil;

public record FolderRes(String id, String name) {
    public FolderRes(Folder folder){
        this(folder.getEncryptId(),folder.getName());
    }

    public Long encryptionId(){
        return EncryptionUtil.decrypt(id);
    }
}
