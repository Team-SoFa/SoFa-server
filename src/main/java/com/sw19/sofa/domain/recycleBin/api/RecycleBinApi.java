package com.sw19.sofa.domain.recycleBin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface RecycleBinApi {
    @Operation(summary = "영구 삭제", description = "링크 카드를 영구 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 카드 영구 삭제 성공 메세지")
    })
    ResponseEntity<String> permanentlyDelete(
            @Schema(description = "링크카드 아이디") String id
    );
}
