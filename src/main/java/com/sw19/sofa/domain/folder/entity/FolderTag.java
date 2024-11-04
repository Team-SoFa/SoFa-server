package com.sw19.sofa.domain.folder.entity;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tag_id")
    private Tag tag;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Builder
    public FolderTag(Tag tag, Folder folder) {
        this.tag = tag;
        this.folder = folder;
    }
    public String getEncryptUserId() {
        return EncryptionUtil.encrypt(this.id);
    }

}
