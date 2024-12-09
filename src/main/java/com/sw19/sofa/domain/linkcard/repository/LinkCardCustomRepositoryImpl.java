package com.sw19.sofa.domain.linkcard.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.linkcard.entity.QLinkCard.linkCard;
import static com.sw19.sofa.domain.linkcard.entity.QLinkCardTag.linkCardTag;

@Repository
@RequiredArgsConstructor
public class LinkCardCustomRepositoryImpl implements LinkCardCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<LinkCard> findAllByFolderIdAndSortCondition(List<Long> folderIdList, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId) {

        return jpaQueryFactory.selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .leftJoin(linkCard.folder).fetchJoin()
                .where(
                        lastIdLt(lastId),
                        folderIdListIn(folderIdList)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    @Override
    public List<LinkCard> findAllByLinkCardTagAndSortCondition(LinkCardTag tag, SortBy sortBy, SortOrder sortOrder, int limit, Long lastId) {
        return jpaQueryFactory.selectFrom(linkCard)
                .leftJoin(linkCard.article).fetchJoin()
                .leftJoin(linkCard.folder).fetchJoin()
                .leftJoin(linkCardTag).fetchJoin()
                .where(
                        linkCardTag.eq(tag),
                        lastIdLt(lastId)
                )
                .orderBy(sortBy.getOrderSpecifier(sortOrder))
                .limit(limit + 1)
                .fetch();
    }

    private static BooleanExpression folderIdListIn(List<Long> folderIdList) {
        return folderIdList == null || folderIdList.isEmpty() ? null : linkCard.folder.id.in(folderIdList);
    }

    private BooleanExpression lastIdLt(Long lastId) {
        return (lastId == null || lastId == 0) ? null : linkCard.id.lt(lastId);
    }

}
