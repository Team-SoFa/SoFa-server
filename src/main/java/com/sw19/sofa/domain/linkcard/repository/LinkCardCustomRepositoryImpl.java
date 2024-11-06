package com.sw19.sofa.domain.linkcard.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;

@Repository
@RequiredArgsConstructor
public class LinkCardCustomRepositoryImpl implements LinkCardCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<LinkCard> findAllByFolderIdAndSortCondition(Long folderId, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId) {

        return jpaQueryFactory.selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .leftJoin(linkCard.folder).fetchJoin()
                .where(
                        lastIdLt(lastId),
                        linkCard.folder.id.eq(folderId)
                )
                .orderBy(sortCondition(sortBy, sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    private BooleanExpression lastIdLt(Long lastId) {
        return (lastId == null || lastId == 0) ? null : linkCard.id.lt(lastId);
    }


    private OrderSpecifier<?> sortCondition(SortBy sortBy, SortOrder sortOrder) {
        Order order = sortOrder == SortOrder.ASCENDING ? Order.ASC : Order.DESC;

        return switch (sortBy) {
            case RECENTLY_SAVED -> new OrderSpecifier<>(order, linkCard.createdAt);
            case RECENTLY_VIEWED -> new OrderSpecifier<>(order, linkCard.visitedAt);
            case MOST_VIEWED -> new OrderSpecifier<>(order, linkCard.views);
        };
    }

}
