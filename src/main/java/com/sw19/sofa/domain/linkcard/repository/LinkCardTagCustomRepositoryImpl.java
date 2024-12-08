package com.sw19.sofa.domain.linkcard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.folder.entity.QFolder;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.entity.QLinkCard;
import com.sw19.sofa.domain.linkcard.entity.QLinkCardTag;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LinkCardTagCustomRepositoryImpl implements LinkCardTagCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public LinkCardTag findMostTagIdByMember(Member member) {
        QLinkCard linkCard = QLinkCard.linkCard;
        QLinkCardTag linkCardTag = QLinkCardTag.linkCardTag;
        QFolder folder = QFolder.folder;

        return jpaQueryFactory
                .select(linkCardTag)
                .from(linkCard)
                .join(linkCardTag).on(linkCard.id.eq(linkCardTag.linkCard.id))
                .join(folder).on(linkCard.folder.id.eq(folder.id))
                .where(folder.member.eq(member))
                .groupBy(linkCardTag.tagId)
                .orderBy(linkCardTag.tagId.count().desc())
                .limit(1)
                .fetchOne();
    }
}
