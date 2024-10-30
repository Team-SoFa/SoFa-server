package com.sw19.sofa.global.common.dto;

import com.sw19.sofa.domain.member.entity.MemberTag;
import com.sw19.sofa.global.util.EncryptionUtil;

public record MemberTagDto(Long id, String name) {
    public MemberTagDto(MemberTag memberTag){
        this(memberTag.getId(), memberTag.getName());
    }
    public String getEncryptId(){
        return EncryptionUtil.encrypt(id);
    }
}
