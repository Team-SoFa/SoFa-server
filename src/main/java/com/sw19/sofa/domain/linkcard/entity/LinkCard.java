package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "folder_id")
    private Folder folder;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
    private String memo;
    @Column(name = "is_share")
    private String isShare;
    @Column(name = "visited_at")
    private LocalDateTime visitedAt;

    @Builder
    public LinkCard(Folder folder, Article article, String memo, String isShare, LocalDateTime visitedAt) {
        this.folder = folder;
        this.article = article;
        this.memo = memo;
        this.isShare = isShare;
        this.visitedAt = visitedAt;
    }
}
