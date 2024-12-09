package com.sw19.sofa.domain.article.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.error.ArticleErrorCode;
import com.sw19.sofa.domain.article.repository.ArticleRepository;
import com.sw19.sofa.global.util.ImageExtractor;
import com.sw19.sofa.global.common.dto.ArticleDto;
import com.sw19.sofa.global.error.code.CommonErrorCode;
import com.sw19.sofa.global.error.exception.BusinessException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final EntityManager entityManager;
    private final ImageExtractor imageExtractor;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY = 1000;

    public Article getArticleByUrl(String url){
        return articleRepository.findByUrl(url).orElseThrow(() -> new BusinessException(ArticleErrorCode.NOT_FOUND_ARTICLE));
    }

    public ArticleDto getArticleDtoByUrlOrElseNull(String url){
        Article article = articleRepository.findByUrl(url).orElse(null);
        return article != null ? new ArticleDto(article) : null;
    }

    @Transactional
    public void enterArticle(Article article) {
        article.enter();
        articleRepository.save(article);
    }

    @Transactional
    public Article addArticle(String url, String title, String summary) {
        String imageUrl = imageExtractor.extractMainImage(url);

        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                Article article = Article.builder()
                        .url(url)
                        .title(title)
                        .summary(summary)
                        .imageUrl(imageUrl)
                        .views(1L)
                        .build();
                return articleRepository.save(article);
            } catch (Exception e) {
                attempts++;
                if (attempts == MAX_RETRIES) {
                    log.error("Failed to save article after {} attempts", MAX_RETRIES, e);
                    throw e;
                }
                log.warn("Attempt {} failed, retrying after delay... url: {}", attempts, url);
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
                }
                entityManager.clear();
            }
        }
        throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
