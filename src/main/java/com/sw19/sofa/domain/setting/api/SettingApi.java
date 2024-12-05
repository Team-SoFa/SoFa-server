package com.sw19.sofa.domain.setting.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.dto.request.ToggleAlarmRequest;
import com.sw19.sofa.domain.setting.dto.response.SettingResponse;
import com.sw19.sofa.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "⚙️ Setting")
public interface SettingApi {
    @Operation(summary = "설정 조회", description = "사용자의 알림 설정을 조회합니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "설정 정보(설정 id, "
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "설정 정보를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<SettingResponse> getSetting(Member member);

    @Operation(summary = "알림 설정 변경", description = "특정 알림 설정을 토글 후 전체 설정 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "설정 정보(설정 id, 리마인드 알림 설정 정보, 추천 알림 설정 정보, 공지사항 및 업데이트 알림 설정 정보)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "설정 정보를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<SettingResponse> toggleAlarm(
            Member member,
            ToggleAlarmRequest request
    );
}