package com.sw19.sofa.domain.article.entity;

import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Long views;
    @Column(name = "image_url")
    private String imageUrl;
    private String summary;

    @Builder
    public Article(String url, Long views, String imageUrl, String summary) {
        this.url = url;
        this.views = views;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }
    public String getEncryptUserId() {
        return EncryptionUtil.encrypt(this.id);
    }

    public void enter() {
        this.views += 1;
    }
}
