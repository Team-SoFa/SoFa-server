package com.sw19.sofa.domain.alarm.dto.response;

import java.util.List;

public record AlarmListRes(
        List<AlarmRes> alarmList
) {
}
