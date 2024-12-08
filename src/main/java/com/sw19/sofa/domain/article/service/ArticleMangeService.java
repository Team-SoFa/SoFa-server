package com.sw19.sofa.domain.article.service;

import com.sw19.sofa.domain.article.dto.response.ArticleRes;
import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.entity.ArticleTag;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.service.LinkCardTagService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.ListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleMangeService {
    private final LinkCardTagService linkCardTagService;
    private final ArticleTagService articleTagService;

    public ListRes<ArticleRes> getRecommend(Member member) {
        //(아티클-태그 아이디 리스트) 생성
        List<ArticleTag> articleAll = articleTagService.getArticleAll();
        Map<Article, List<Long>> articleListMap = articleAll.stream().collect(
                Collectors.groupingBy(ArticleTag::getArticle, Collectors.mapping(ArticleTag::getId, Collectors.toList()))
        );

        //사용자 태그에 대해 (태그 아이디-태그 빈도 횟수) 리스트 생성
        Map<LinkCardTag, Long> linkCardTagStatisticsByMember = linkCardTagService.getTagStatisticsByMember(member);
        Map<Long,Long> userTagIdStatistics = linkCardTagStatisticsByMember.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getTagId(),
                        Map.Entry::getValue
                ));

        //사용자와 아티클 코사인 점수 계산
        Map<Article, Double> articleScoreList = new HashMap<>();
        long totalUserTagCount = userTagIdStatistics.values().stream().mapToLong(Long::longValue).sum();
        for (Article article : articleListMap.keySet()) {
            double similarity = calculateAdjustedSimilarity(userTagIdStatistics, article.getViews() ,articleListMap.get(article), totalUserTagCount);
            articleScoreList.put(article, similarity);
        }

        //코사인 점수에 따른 아티클 정렬 후 상위 6개 선택
        List<Article> resultArticleSortList = articleScoreList.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(6)
                .map(Map.Entry::getKey)
                .toList();

        List<ArticleRes> data = resultArticleSortList.stream().map(ArticleRes::new).toList();
        return new ListRes<>(data,6,data.size(),false);
    }

    //(코사인 점수 * log(조회수)) 최종 유사도 점수 계산
    private double calculateAdjustedSimilarity(Map<Long, Long> userTagStatistics,Long views ,List<Long> tagIdList, Long totalUserTagCount) {
        double baseSimilarity = calculateSimilarity(userTagStatistics, tagIdList, totalUserTagCount);
        return baseSimilarity * Math.log(1 + views);
    }

    //아티클 태그와 사용자 태그 빈도에 따른 코사인 점수 계산
    private double calculateSimilarity(Map<Long, Long> userTagIdStatistics, List<Long> tagIdList, Long totalUserTagCount) {
        double similarityScore = 0.0;

        for (Long tagId : tagIdList) {
            if (userTagIdStatistics.containsKey(tagId)) {
                double tagWeight = (double) userTagIdStatistics.get(tagId) / totalUserTagCount;
                similarityScore += tagWeight;
            }
        }
        return similarityScore;
    }
}
