package com.sw19.sofa.domain.setting.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.entity.Setting;
import com.sw19.sofa.domain.setting.enums.AlarmType;
import com.sw19.sofa.domain.setting.error.SettingErrorCode;
import com.sw19.sofa.domain.setting.repository.SettingRepository;
import com.sw19.sofa.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettingService {
    private final SettingRepository settingRepository;

    @Transactional
    public void setNewUser(Member member) {
        if (settingRepository.existsByMember(member)) {
            throw new BusinessException(SettingErrorCode.SETTING_ALREADY_EXISTS);
        }

        Setting setting = Setting.builder()
                .member(member)
                .is_recommend(true)
                .is_remind(true)
                .is_notice(true)
                .build();

        settingRepository.save(setting);
    }

    public Setting getMemberSetting(Member member) {
        return settingRepository.findByMember(member)
                .orElseGet(() -> {
                    Setting setting = Setting.builder()
                            .member(member)
                            .is_remind(true)
                            .is_recommend(true)
                            .is_notice(true)
                            .build();
                    return settingRepository.save(setting);
                });
    }

    @Transactional
    public void toggleAlarm(Member member, AlarmType alarmType) {
        Setting setting = getMemberSetting(member);

        switch (alarmType) {
            case REMIND -> setting.toggleRemindAlarm();
            case RECOMMEND -> setting.toggleRecommendAlarm();
            case NOTICE -> setting.toggleNoticeAlarm();
            default -> throw new BusinessException(SettingErrorCode.INVALID_ALARM_TYPE);
        }

        settingRepository.save(setting);
    }

    public boolean isRemindAlarmEnabled(Member member) {
        Setting setting = getMemberSetting(member);
        return setting.getIs_remind();
    }

    public boolean isRecommendAlarmEnabled(Member member) {
        Setting setting = getMemberSetting(member);
        return setting.getIs_recommend();
    }

    public boolean isNoticeAlarmEnabled(Member member) {
        Setting setting = getMemberSetting(member);
        return setting.getIs_notice();
    }
}
