package com.sw19.sofa.domain.setting.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 타입")
public enum AlarmType {
    @Schema(description = "리마인드 알림")
    REMIND,

    @Schema(description = "추천 알림")
    RECOMMEND,

    @Schema(description = "공지사항 알림")
    NOTICE
}