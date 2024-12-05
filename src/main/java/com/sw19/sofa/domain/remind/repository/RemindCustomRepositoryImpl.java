package com.sw19.sofa.domain.remind.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.remind.entity.QRemind.remind;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;

@Repository
@RequiredArgsConstructor
public class RemindCustomRepositoryImpl implements RemindCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Remind> search(
            Long memberId,
            Long lastId,
            int limit,
            RemindSortBy sortBy,
            SortOrder sortOrder
    ) {
        return queryFactory
                .selectFrom(remind)
                .leftJoin(remind.linkCard, linkCard).fetchJoin()
                .where(
                        remind.member.id.eq(memberId),
                        lastId != null ? remind.id.lt(lastId) : null
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }
}