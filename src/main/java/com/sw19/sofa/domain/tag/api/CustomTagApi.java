package com.sw19.sofa.domain.tag.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.dto.request.CustomTagReq;
import com.sw19.sofa.domain.tag.dto.response.CustomTagRes;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "📌 Custom Tag")
public interface CustomTagApi {
    @Operation(summary = "커스텀 태그 생성", description = "새로운 커스텀 태그를 생성합니다")
    ResponseEntity<CustomTagRes> createCustomTag(@AuthMember Member member, @RequestBody CustomTagReq req);

    @Operation(summary = "커스텀 태그 수정", description = "기존 커스텀 태그를 수정합니다")
    ResponseEntity<CustomTagRes> updateCustomTag(
            @AuthMember Member member,
            @PathVariable String id,
            @RequestBody CustomTagReq req
    );

    @Operation(summary = "커스텀 태그 삭제", description = "커스텀 태그를 삭제합니다")
    ResponseEntity<Void> deleteCustomTag(@AuthMember Member member, @PathVariable String id);
}