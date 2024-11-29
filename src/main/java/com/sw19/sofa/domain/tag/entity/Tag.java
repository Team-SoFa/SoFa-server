package com.sw19.sofa.domain.tag.entity;

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
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public String getEncryptedId() {
        return EncryptionUtil.encrypt(this.id);
    }
}