package com.sw19.sofa.domain.searchbox.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.searchbox.enums.SearchBoxSortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCardTag.linkCardTag;

@Repository
@RequiredArgsConstructor
public class SearchBoxCustomRepositoryImpl implements SearchBoxCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<LinkCard> searchByFolder(
            Long folderId,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        linkCard.folder.id.eq(folderId),
                        keywordContains(keyword)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> searchByTags(
            List<Long> tagIds,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        tagsExpression(tagIds),
                        keywordContains(keyword)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> searchByTagsAndFolder(
            List<Long> tagIds,
            Long folderId,
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        linkCard.folder.id.eq(folderId),
                        tagsExpression(tagIds),
                        keywordContains(keyword)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> searchAll(
            String keyword,
            Long lastId,
            int limit,
            SearchBoxSortBy sortBy,
            SortOrder sortOrder
    ) {
        return queryFactory
                .selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .where(
                        lastIdExpression(lastId),
                        keywordContains(keyword)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    private BooleanExpression lastIdExpression(Long lastId) {
        return lastId != null && lastId > 0 ? linkCard.id.gt(lastId) : null;
    }

    private BooleanExpression keywordContains(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return linkCard.title.containsIgnoreCase(keyword)
                .or(linkCard.article.url.containsIgnoreCase(keyword))
                .or(linkCard.summary.containsIgnoreCase(keyword))
                .or(linkCard.memo.containsIgnoreCase(keyword));
    }

    private BooleanExpression tagsExpression(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return null;
        }
        return linkCard.id.in(
                JPAExpressions
                        .select(linkCardTag.linkCard.id)
                        .from(linkCardTag)
                        .where(linkCardTag.tagId.in(tagIds))
        );
    }
}
