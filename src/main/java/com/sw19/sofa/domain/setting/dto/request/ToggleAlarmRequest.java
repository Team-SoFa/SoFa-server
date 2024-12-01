package com.sw19.sofa.domain.setting.dto.request;

import com.sw19.sofa.domain.setting.enums.AlarmType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ToggleAlarmRequest(
        @Schema(description = "알림 타입", example = "REMIND")
        AlarmType alarmType
) {}