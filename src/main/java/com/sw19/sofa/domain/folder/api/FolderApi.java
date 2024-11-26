package com.sw19.sofa.domain.folder.api;

import com.sw19.sofa.domain.folder.dto.request.FolderReq;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "\uD83D\uDCC1 Folder")
public interface FolderApi {
    @Operation(summary = "전체 폴더 목록 조회", description = "전체 폴더 목록에 대해 아이디와 이름 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 폴더(아이디, 폴더 이름) 리스트 반환")
    })
    ResponseEntity<FolderListRes> getFolderList(Member member);

    @Operation(summary = "폴더 추가", description = "새로운 폴더를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "폴더 추가 후 전체 폴더(아이디, 폴더 이름) 리스트 반환")
    })
    ResponseEntity<FolderListRes> addFolder(Member member, FolderReq req);

    @Operation(summary = "폴더 삭제", description = "폴더 아이디를 통해 폴더를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "폴더 삭제 완료"),
            @ApiResponse(responseCode = "404", description = "code: F-001 | message: 존재하지 않는 폴더입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<String> delFolder(String id, Member member);

    @Operation(summary = "폴더 수정", description = "폴더 아이디를 통해 폴더 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 후 폴더 정보(아이디, 이름)"),
            @ApiResponse(responseCode = "404", description = "code: F-001 | message: 존재하지 않는 폴더입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<FolderRes> editFolder(String id, FolderReq req);
}
