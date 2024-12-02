package com.sw19.sofa.domain.alarm.api;

import com.sw19.sofa.domain.alarm.dto.response.AlarmListRes;
import com.sw19.sofa.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "⏰ Alarm")
public interface AlarmApi {
    @Operation(summary = "알림 목록 조회", description = "사용자의 알람 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "알림 정보(id, 타입(REMIND:리마인드, RECOMMEND: 추천, UPDATE: 업데이트, NOTICE: 공지사항), 내용, 시간, 읽음 여부)")
    ResponseEntity<AlarmListRes> getAlarmList(Member member);

}
