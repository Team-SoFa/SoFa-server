package com.sw19.sofa.domain.folder.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.folder.entity.QFolder;
import com.sw19.sofa.domain.folder.entity.QFolderTag;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.entity.QTag;
import com.sw19.sofa.global.common.dto.FolderWithTagListDto;
import com.sw19.sofa.global.common.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FolderTagCustomRepositoryImpl implements FolderTagCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FolderWithTagListDto> findFolderWithTagListByMember(Member member) {
        QFolder folder = QFolder.folder;
        QFolderTag folderTag = QFolderTag.folderTag;
        QTag tag = QTag.tag;


        return jpaQueryFactory
                .select(Projections.constructor(FolderWithTagListDto.class,
                        folder.id,
                        folder.name,
                        Projections.list(
                                Projections.constructor(TagDto.class, tag.id, tag.name)
                        )
                ))
                .from(folderTag)
                .join(folderTag.folder, folder)
                .join(folderTag.tag, tag)
                .where(folder.member.eq(member))
                .fetch();

    }
}
