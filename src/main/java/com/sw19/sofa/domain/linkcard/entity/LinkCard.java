package com.sw19.sofa.domain.linkcard.entity;

import com.sw19.sofa.domain.folder.entity.Folder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String url;
    @Column(name = "is_share")
    private String isShare;
    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public LinkCard(Folder folder, String url, String isShare, String imageUrl) {
        this.folder = folder;
        this.url = url;
        this.isShare = isShare;
        this.imageUrl = imageUrl;
    }
}
