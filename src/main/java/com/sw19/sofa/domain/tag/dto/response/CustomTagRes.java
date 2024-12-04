package com.sw19.sofa.domain.tag.dto.response;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.Getter;

@Getter
public class CustomTagRes {
    private final String name;
    private final String encryptedId;
    private final String memberId;  // Long -> String으로 변경

    public CustomTagRes(CustomTag customTag) {
        this.name = customTag.getName();
        this.encryptedId = customTag.getEncryptedId();
        this.memberId = customTag.getMember().getId() != null ?
                EncryptionUtil.encrypt(customTag.getMember().getId()) : null;
    }
}