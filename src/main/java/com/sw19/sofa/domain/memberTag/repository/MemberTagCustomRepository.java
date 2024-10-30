package com.sw19.sofa.domain.memberTag.repository;

import com.sw19.sofa.domain.member.entity.MemberTag;

import java.util.List;

public interface MemberTagCustomRepository{
    List<MemberTag> findAllByLinkCardId(Long linkCardId);
}
