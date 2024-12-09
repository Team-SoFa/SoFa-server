package com.sw19.sofa.domain.article.api;

import com.sw19.sofa.domain.article.dto.response.ArticleRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "\uD83D\uDCDC Article")
public interface ArticleApi {
    @Operation(summary = "아티클 추천")
    @ApiResponse(responseCode = "200", description = "사용자 정보(이메일, 이름)")
    ResponseEntity<ListRes<ArticleRes>> getRecommend(@AuthMember Member member);
}
