package com.sw19.sofa.domain.alarm.service;

import com.sw19.sofa.domain.alarm.dto.response.AlarmListRes;
import com.sw19.sofa.domain.alarm.dto.response.AlarmRes;
import com.sw19.sofa.domain.alarm.entity.Alarm;
import com.sw19.sofa.domain.alarm.error.AlarmErrorCode;
import com.sw19.sofa.domain.alarm.repository.AlarmRepository;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public AlarmListRes getAlarmList(Member member) {
        List<Alarm> alarmList = alarmRepository.findAllByMember(member);
        List<AlarmRes> alarmResList = alarmList.stream().map(AlarmRes::new).toList();

        return new AlarmListRes(alarmResList);
    }

    @Transactional
    public void readAlarm(String encryptId) {
        Long id = EncryptionUtil.decrypt(encryptId);
        Alarm alarm = alarmRepository.findById(id).orElseThrow(() -> new BusinessException(AlarmErrorCode.NOT_FOUND_ALARM));
        alarm.setRead();
        alarmRepository.save(alarm);
    }
}
