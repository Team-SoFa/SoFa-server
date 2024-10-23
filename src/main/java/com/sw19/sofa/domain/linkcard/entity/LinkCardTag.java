package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.linkcard.entity.enums.TagType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkCardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "linkCard_id")
    private LinkCard linkCard;
    @Column(name = "tag_id")
    private Long tagId;
    @Column(name = "tag_type")
    private TagType tagType;

    @Builder
    public LinkCardTag(LinkCard linkCard, Long tagId, TagType tagType) {
        this.linkCard = linkCard;
        this.tagId = tagId;
        this.tagType = tagType;
    }
}
