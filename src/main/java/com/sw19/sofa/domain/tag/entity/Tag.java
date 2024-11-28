package com.sw19.sofa.domain.tag.entity;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_tag_name", columnList = "name"),
                @Index(name = "idx_tag_type", columnList = "type")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private TagType type;

    @Builder
    public Tag(String name, TagType type) {
        this.name = name;
        this.type = type;
    }

    public String getEncryptedId() {
        return EncryptionUtil.encrypt(this.id);
    }
}