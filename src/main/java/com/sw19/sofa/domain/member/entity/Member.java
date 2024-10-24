package com.sw19.sofa.domain.member.entity;

import com.sw19.sofa.domain.member.entity.enums.Authority;
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
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String email, Authority authority) {
        this.email = email;
        this.authority = authority;
    }

    public String getEncryptUserId() {
        return EncryptionUtil.encrypt(this.id);
    }

    public Long decryptUserId(String encryptedUserId) {
        return EncryptionUtil.decrypt(encryptedUserId);
    }
}
