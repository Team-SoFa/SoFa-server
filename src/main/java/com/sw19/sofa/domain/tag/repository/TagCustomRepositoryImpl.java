package com.sw19.sofa.domain.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw19.sofa.domain.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sw19.sofa.domain.article.entity.QArticleTag.articleTag;
import static com.sw19.sofa.domain.tag.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tag> findAllByArticleId(Long articleId) {
        return jpaQueryFactory.select(tag)
                .from(articleTag)
                .join(articleTag.tag, tag)
                .where(articleTag.article.id.eq(articleId))
                .fetch();
    }
}
