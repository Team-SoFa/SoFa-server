package com.sw19.sofa.domain.setting.error;

import com.sw19.sofa.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SettingErrorCode implements ErrorCode {
    SETTING_NOT_FOUND(HttpStatus.NOT_FOUND, "S-001", "설정 정보를 찾을 수 없습니다."),
    INVALID_ALARM_TYPE(HttpStatus.BAD_REQUEST, "S-002", "유효하지 않은 알림 타입입니다."),
    SETTING_ALREADY_EXISTS(HttpStatus.CONFLICT, "S-003", "이미 설정이 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}