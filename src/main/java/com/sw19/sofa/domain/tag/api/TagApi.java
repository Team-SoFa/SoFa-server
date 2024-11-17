package com.sw19.sofa.domain.tag.api;

import com.sw19.sofa.domain.tag.dto.request.CustomTagReq;
import com.sw19.sofa.domain.tag.dto.response.CustomTagRes;
import com.sw19.sofa.domain.tag.dto.request.TagReq;
import com.sw19.sofa.domain.tag.dto.response.TagRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "📌 Tag")
public interface TagApi {

    @Operation(summary = "모든 사용자 정의 태그 가져오기", description = "특정 사용자와 관련된 모든 사용자 정의 태그를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 사용자 정의 태그 목록이 성공적으로 반환되었습니다.")
    })
    ResponseEntity<List<CustomTagRes>> getAllCustomTags(Member member);

    @Operation(summary = "사용자 정의 태그 추가", description = "특정 사용자에게 새로운 사용자 정의 태그를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정의 태그가 성공적으로 추가되었으며 응답으로 반환됩니다.")
    })
    ResponseEntity<CustomTagRes> addCustomTag(Member member, CustomTagReq req);

    @Operation(summary = "사용자 정의 태그 삭제", description = "ID를 통해 사용자 정의 태그를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정의 태그가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자 정의 태그를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<String> deleteCustomTag(String id);

    @Operation(summary = "모든 태그 가져오기", description = "사용 가능한 모든 태그를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 태그 목록이 성공적으로 반환되었습니다.")
    })
    ResponseEntity<List<TagRes>> getAllTags();

    @Operation(summary = "태그 추가", description = "새로운 태그를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "태그가 성공적으로 추가되었으며 응답으로 반환됩니다.")
    })
    ResponseEntity<TagRes> addTag(TagReq req);

    @Operation(summary = "태그 삭제", description = "ID를 통해 태그를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "태그가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "태그를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<String> deleteTag(String id);
}
