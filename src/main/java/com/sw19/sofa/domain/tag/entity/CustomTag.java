package com.sw19.sofa.domain.tag.entity;

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
public class CustomTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    public void updateName(String name) {
        this.name = name;
    }

    @Builder
    public CustomTag(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    public String getEncryptedId() {
        return EncryptionUtil.encrypt(this.id);
    }
}