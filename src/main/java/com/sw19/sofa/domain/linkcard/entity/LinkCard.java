package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.global.common.entity.BaseTimeEntity;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkCard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "folder_id")
    private Folder folder;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
    private String title;
    private String memo;
    private String summary;
    private Long views;
    @Column(name = "visited_at")
    private LocalDateTime visitedAt;

    @Builder
    public LinkCard(Folder folder, Article article, String title, String memo, String summary, Long views, LocalDateTime visitedAt) {
        this.folder = folder;
        this.article = article;
        this.title = title;
        this.memo = memo;
        this.summary = summary;
        this.views = views;
        this.visitedAt = visitedAt;
    }
    public String getEncryptId() {
        return EncryptionUtil.encrypt(this.id);
    }
    public void editInfo(String title, String memo, String summary){
        this.title = title;
        this.memo = memo;
        this.summary = summary;
    }
}
