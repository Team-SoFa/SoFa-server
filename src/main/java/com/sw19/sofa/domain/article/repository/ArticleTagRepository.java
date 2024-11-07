package com.sw19.sofa.domain.article.repository;

import com.sw19.sofa.domain.article.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findAllByArticle_Id(Long articleId);
}
