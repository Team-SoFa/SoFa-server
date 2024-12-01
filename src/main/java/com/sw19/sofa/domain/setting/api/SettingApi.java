package com.sw19.sofa.domain.setting.api;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.dto.request.ToggleAlarmRequest;
import com.sw19.sofa.domain.setting.dto.response.SettingResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "⚙️ Setting")
public interface SettingApi {
    @Operation(summary = "설정 조회", description = "사용자의 알림 설정을 조회합니다")
    ResponseEntity<SettingResponse> getSetting(@AuthMember Member member);

    @Operation(summary = "알림 설정 변경", description = "특정 알림 설정을 토글합니다")
    ResponseEntity<SettingResponse> toggleAlarm(
            @AuthMember Member member,
            ToggleAlarmRequest request
    );
}