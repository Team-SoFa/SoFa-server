package com.sw19.sofa.domain.alarm.controller;

import com.sw19.sofa.domain.alarm.api.AlarmApi;
import com.sw19.sofa.domain.alarm.dto.response.AlarmListRes;
import com.sw19.sofa.domain.alarm.service.AlarmService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController implements AlarmApi {
    private final AlarmService alarmService;

    @Override
    @GetMapping
    public ResponseEntity<AlarmListRes> getAlarmList(@AuthMember Member member) {
        AlarmListRes res = alarmService.getAlarmList(member);
        return BaseResponse.ok(res);
    }
}
