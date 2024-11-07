package com.sw19.sofa.domain.article.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.error.ArticleErrorCode;
import com.sw19.sofa.domain.article.repository.ArticleRepository;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Article getArticleByUrl(String url){
        return articleRepository.findByUrl(url).orElseThrow(() -> new BusinessException(ArticleErrorCode.NOT_FOUND_ARTICLE));
    }

    @Transactional
    public void enterArticle(Article article) {
        article.enter();
        articleRepository.save(article);
    }
}
