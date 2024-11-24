package com.sw19.sofa.domain.searchbox.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.global.common.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCardTag.linkCardTag;

@Repository
@RequiredArgsConstructor
public class SearchBoxCustomRepositoryImpl implements SearchBoxCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<LinkCard> searchByFolder(Long folderId, Long lastId, int limit, SearchBoxSortBy sortBy, SortOrder sortOrder) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        linkCard.folder.id.eq(folderId)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> searchByTags(List<Long> tagIds, Long lastId, int limit, SearchBoxSortBy sortBy, SortOrder sortOrder) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        linkCard.id.in(
                                JPAExpressions
                                        .select(linkCardTag.linkCard.id)
                                        .from(linkCardTag)
                                        .where(linkCardTag.tagId.in(tagIds))
                        )
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> searchAll(Long lastId, int limit, SearchBoxSortBy sortBy, SortOrder sortOrder) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(lastIdExpression(lastId))
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    private BooleanExpression lastIdExpression(Long lastId) {
        return lastId != null && lastId > 0 ? linkCard.id.gt(lastId) : null;
    }
}