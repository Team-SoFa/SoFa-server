package com.sw19.sofa.domain.setting.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.entity.Setting;
import com.sw19.sofa.domain.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;

    @Transactional
    public void setNewUser(Member member){
        Setting setting = Setting.builder()
                .member(member)
                .is_recommend(true)
                .is_remind(true)
                .is_notice(true)
                .build();

        settingRepository.save(setting);
    }
}
