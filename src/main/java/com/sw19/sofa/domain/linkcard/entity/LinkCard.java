package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.folder.entity.Folder;
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
    private String title;
    private String memo;
    @Column(name = "is_share")
    private Boolean isShare;
    @Column(name = "is_alarm")
    private Boolean isAlarm;
    @Column(name = "visited_at")
    private LocalDateTime visitedAt;

    @Builder
    public LinkCard(Folder folder, Article article, String title, String memo, Boolean isShare, Boolean isAlarm, LocalDateTime visitedAt) {
        this.folder = folder;
        this.article = article;
        this.title = title;
        this.memo = memo;
        this.isShare = isShare;
        this.isAlarm = isAlarm;
        this.visitedAt = visitedAt;
    }

    public String getEncryptUserId() {
        return EncryptionUtil.encrypt(this.id);
    }
}
