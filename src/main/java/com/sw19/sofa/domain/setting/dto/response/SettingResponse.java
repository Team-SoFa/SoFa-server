package com.sw19.sofa.domain.setting.dto.response;

import com.sw19.sofa.domain.setting.entity.Setting;

public record SettingResponse(
        String encryptedId,
        boolean isRemindAlarm,
        boolean isRecommendAlarm,
        boolean isNoticeAlarm
) {
    public static SettingResponse from(Setting setting) {
        return new SettingResponse(
                setting.getEncryptId(),
                setting.getIs_remind(),
                setting.getIs_recommend(),
                setting.getIs_notice()
        );
    }
}