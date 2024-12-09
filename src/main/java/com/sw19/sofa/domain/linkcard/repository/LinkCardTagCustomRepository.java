package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.member.entity.Member;

import java.util.Map;

public interface LinkCardTagCustomRepository {
    LinkCardTag findMostTagIdByMember(Member member);
    Map<LinkCardTag ,Long> findTagStatisticsByMember(Member member);
}
