package com.sw19.sofa.domain.tag.entity;

import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Builder
    public Tag(String name) {
        this.name = name;
    }
    public String getEncryptUserId() {
        return EncryptionUtil.encrypt(this.id);
    }

}
