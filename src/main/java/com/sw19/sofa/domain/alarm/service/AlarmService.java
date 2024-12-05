package com.sw19.sofa.domain.alarm.service;

import com.sw19.sofa.domain.alarm.dto.response.AlarmListRes;
import com.sw19.sofa.domain.alarm.dto.response.AlarmRes;
import com.sw19.sofa.domain.alarm.entity.Alarm;
import com.sw19.sofa.domain.alarm.enums.AlarmType;
import com.sw19.sofa.domain.alarm.error.AlarmErrorCode;
import com.sw19.sofa.domain.alarm.repository.AlarmRepository;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Transactional
    public void addRemindAlarm(Member member, String content){
        Alarm alarm = Alarm.builder()
                .member(member)
                .content(content)
                .type(AlarmType.REMIND)
                .isRead(Boolean.FALSE)
                .build();

        alarmRepository.save(alarm);
    }

    @Transactional
    public void deleteExpiredAlarm() {
        List<Alarm> alarmList = alarmRepository.findAll();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = currentDateTime.minusMonths(3);

        List<Alarm> deleteList = alarmList.stream().filter(
                alarm -> alarm.getCreatedAt().isBefore(threeMonthsAgo)
        ).toList();
        alarmRepository.deleteAll(deleteList);
    }

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
