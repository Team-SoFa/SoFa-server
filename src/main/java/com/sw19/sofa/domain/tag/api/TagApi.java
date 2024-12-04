package com.sw19.sofa.domain.tag.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.dto.response.TagRes;
import com.sw19.sofa.domain.tag.dto.response.TagSearchRes;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "📌 Tag")
public interface TagApi {
    @Operation(summary = "전체 태그 목록 조회", description = "AI 태그와 커스텀 태그 목록을 모두 조회합니다")
    ResponseEntity<List<TagSearchRes>> getAllTags(@AuthMember Member member);

    @Operation(summary = "AI 태그 삭제", description = "AI 태그를 삭제합니다")
    ResponseEntity<Void> deleteTag(@PathVariable String id);
}