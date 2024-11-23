package com.sw19.sofa.domain.tag.entity;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;

    @Builder
    public CustomTag(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    public String getEncryptId() {
        return EncryptionUtil.encrypt(this.id);
    }
}