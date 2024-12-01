package com.sw19.sofa.domain.setting.dto.request;

import com.sw19.sofa.domain.setting.enums.AlarmType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ToggleAlarmRequest(
        @NotNull(message = "알림 타입은 필수입니다")
        @Schema(description = "알림 타입", example = "REMIND")
        AlarmType alarmType
) {}