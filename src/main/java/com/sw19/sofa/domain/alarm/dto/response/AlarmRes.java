package com.sw19.sofa.domain.alarm.dto.response;

import com.sw19.sofa.domain.alarm.entity.Alarm;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AlarmRes(
        @Schema(description = "아이디")
        String id,
        @Schema(description = "알림 종류(REMIND:리마인드, RECOMMEND: 추천, UPDATE: 업데이트, NOTICE: 공지사항)")
        String type,
        @Schema(description = "내용")
        String content,
        @Schema(description = "알림 날짜")
        LocalDateTime time
) {
    public AlarmRes(Alarm alarm) {
        this(alarm.getEncryptId(), alarm.getType().name(), alarm.getContent(), alarm.getCreatedAt());
    }
}
