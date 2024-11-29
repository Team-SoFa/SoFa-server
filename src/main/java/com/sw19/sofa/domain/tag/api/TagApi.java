package com.sw19.sofa.domain.tag.api;

import com.sw19.sofa.domain.tag.dto.response.TagRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "📌 Tag")
public interface TagApi {

    @Operation(summary = "AI 태그 검색", description = "키워드로 AI 태그를 검색합니다")
    ResponseEntity<List<TagRes>> searchTags(@RequestParam String keyword);

    @Operation(summary = "AI 태그 삭제", description = "AI 태그를 삭제합니다")
    ResponseEntity<Void> deleteTag(@PathVariable String id);
}