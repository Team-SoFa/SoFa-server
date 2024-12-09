package com.sw19.sofa.domain.linkcard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.sw19.sofa.domain.folder.entity.QFolder.folder;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCardTag.linkCardTag;

@Repository
@RequiredArgsConstructor
public class LinkCardTagCustomRepositoryImpl implements LinkCardTagCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public LinkCardTag findMostTagIdByMember(Member member) {
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

    @Override
    public Map<LinkCardTag ,Long> findTagStatisticsByMember(Member member) {
        return jpaQueryFactory
                .select(linkCardTag, linkCardTag.count().intValue())
                .from(linkCard)
                .join(linkCardTag).on(linkCard.id.eq(linkCardTag.linkCard.id))
                .join(folder).on(linkCard.folder.id.eq(folder.id))
                .where(
                        folder.member.eq(member),
                        linkCardTag.tagType.eq(TagType.AI)
                )
                .transform(groupBy(linkCardTag).as(linkCardTag.count()));
    }
}
