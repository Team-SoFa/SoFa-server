package com.sw19.sofa.domain.memberTag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.member.entity.MemberTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.linkcard.entity.QLinkCardTag.linkCardTag;
import static com.sw19.sofa.domain.member.entity.QMemberTag.memberTag;

@Repository
@RequiredArgsConstructor
public class MemberTagCustomRepositoryImpl implements MemberTagCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MemberTag> findAllByLinkCardId(Long linkCardId) {
        return jpaQueryFactory.select(memberTag)
                .from(linkCardTag)
                .join(linkCardTag.memberTag, memberTag)
                .where(linkCardTag.linkCard.id.eq(linkCardId))
                .fetch();
    }
}
