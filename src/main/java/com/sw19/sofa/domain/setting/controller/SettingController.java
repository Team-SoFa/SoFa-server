package com.sw19.sofa.domain.setting.controller;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.api.SettingApi;
import com.sw19.sofa.domain.setting.dto.request.ToggleAlarmRequest;
import com.sw19.sofa.domain.setting.dto.response.SettingResponse;
import com.sw19.sofa.domain.setting.service.SettingService;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class SettingController implements SettingApi {
    private final SettingService settingService;

    @Override
    @GetMapping
    public ResponseEntity<SettingResponse> getSetting(@AuthMember Member member) {
        SettingResponse res = settingService.getMemberSetting(member);
        return BaseResponse.ok(res);
    }

    @Override
    @PatchMapping("/alarm")
    public ResponseEntity<SettingResponse> toggleAlarm(
            @AuthMember Member member,
            @Valid @RequestBody ToggleAlarmRequest request
    ) {
        SettingResponse res = settingService.toggleAlarm(member, request.alarmType());
        return BaseResponse.ok(res);
    }
}