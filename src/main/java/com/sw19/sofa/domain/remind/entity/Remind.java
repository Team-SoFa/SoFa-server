package com.sw19.sofa.domain.remind.entity;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.entity.BaseTimeEntity;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Remind extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "linkCard_id")
    private LinkCard linkCard;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Remind(LinkCard linkCard, Member member) {
        this.linkCard = linkCard;
        this.member = member;
    }
    public String getEncryptId() {
        return EncryptionUtil.encrypt(this.id);
    }
}
