package com.sw19.sofa.domain.remind.dto;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.member.entity.Member;

import java.util.List;

public record MemberUnUsedLinkCardListDto(
        Member member,
        List<LinkCard> linkCardList
) {
}
