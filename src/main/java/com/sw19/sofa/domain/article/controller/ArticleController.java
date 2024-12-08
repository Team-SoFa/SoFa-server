package com.sw19.sofa.domain.article.controller;

import com.sw19.sofa.domain.article.api.ArticleApi;
import com.sw19.sofa.domain.article.dto.response.ArticleRes;
import com.sw19.sofa.domain.article.service.ArticleMangeService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.global.common.dto.ListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController implements ArticleApi {

    private final ArticleMangeService articleMangeService;

    @Override
    @GetMapping("/recommend")
    public ResponseEntity<ListRes<ArticleRes>> getRecommend(Member member) {
        ListRes<ArticleRes> res = articleMangeService.getRecommend(member);
        return BaseResponse.ok(res);
    }
}
