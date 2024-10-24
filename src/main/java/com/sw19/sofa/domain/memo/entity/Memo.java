package com.sw19.sofa.domain.memo.entity;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "linkCard_id")
    private LinkCard linkCard;
    private String comment;

    @Builder
    public Memo(LinkCard linkCard, String comment) {
        this.linkCard = linkCard;
        this.comment = comment;
    }
}
