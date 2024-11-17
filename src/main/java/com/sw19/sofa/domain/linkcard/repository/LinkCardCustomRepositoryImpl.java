package com.sw19.sofa.domain.linkcard.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.global.common.enums.SortBy;
import com.sw19.sofa.global.common.enums.SortOrder;
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
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    private BooleanExpression lastIdLt(Long lastId) {
        return (lastId == null || lastId == 0) ? null : linkCard.id.lt(lastId);
    }

}
