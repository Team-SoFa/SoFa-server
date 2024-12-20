package com.sw19.sofa.domain.article.service;

import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.entity.ArticleTag;
import com.sw19.sofa.domain.article.repository.ArticleTagRepository;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.global.common.dto.ArticleTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleTagService {
    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTag> getArticleAll(){
        return articleTagRepository.findAll();
    }

    public List<ArticleTagDto> getArticleTagDtoListByArticleId(Long articleId){
        return articleTagRepository.findAllByArticle_Id(articleId).stream().map(ArticleTagDto::new).toList();
    }

    @Transactional
    public void addArticleTagListByArticleAndTagListIn(Article article, List<Tag> tagList) {
        List<ArticleTag> articleTagList = tagList.stream().map(
                tag -> ArticleTag.builder()
                        .article(article)
                        .tag(tag)
                        .build()
        ).toList();

        articleTagRepository.saveAll(articleTagList);
    }
}
