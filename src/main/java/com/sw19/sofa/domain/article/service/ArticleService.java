package com.sw19.sofa.domain.article.service;

import com.sw19.sofa.domain.ai.service.AiSummaryService;
import com.sw19.sofa.domain.ai.service.WebScrapingService;
import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.error.ArticleErrorCode;
import com.sw19.sofa.domain.article.repository.ArticleRepository;
import com.sw19.sofa.global.common.dto.ArticleDto;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AiSummaryService aiSummaryService;


    @Transactional(readOnly = true)
    public Article getArticleByUrl(String url){
        return articleRepository.findByUrl(url).orElseThrow(() -> new BusinessException(ArticleErrorCode.NOT_FOUND_ARTICLE));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticleDtoByUrlOrElseNull(String url){
        return new ArticleDto(articleRepository.findByUrl(url).orElse(null));
    }
    @Transactional
    public void enterArticle(Article article) {
        article.enter();
        articleRepository.save(article);
    }

    @Transactional
    public Article addArticle(String title, String summary, String imageUrl) {
        Article article = Article.builder()
                .title(title)
                .summary(summary)
                .imageUrl(imageUrl)
                .views(1L)
                .build();

        return articleRepository.save(article);
    }

    @Transactional
    public void updateArticleSummary(Article article) {
        // URL의 내용을 AI로 요약
        String summary = aiSummaryService.summarizeUrl(article.getUrl());
        // 요약 내용 업데이트
        article.updateSummary(summary);
        articleRepository.save(article);
    }
}
