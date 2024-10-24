package com.sw19.sofa.domain.folder.api;

import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "\uD83D\uDCC1 Folder")
public interface FolderApi {
    @Operation(summary = "전체 폴더 목록 조회", description = "전체 폴더 목록에 대해 아이디와 이름 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 폴더 정보(아이디, 폴더 이름) 리스트")
    })
    ResponseEntity<FolderListRes> getFolderList(Member member);
}
