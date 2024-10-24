package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.member.entity.MemberTag;
import com.sw19.sofa.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkCardTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "linkCard_id")
    private LinkCard linkCard;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_tag_id")
    private MemberTag memberTag;

    @Builder
    public LinkCardTag(LinkCard linkCard, MemberTag memberTag) {
        this.linkCard = linkCard;
        this.memberTag = memberTag;
    }
}
