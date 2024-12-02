package com.sw19.sofa.domain.alarm.service;

import com.sw19.sofa.domain.alarm.entity.Alarm;
import com.sw19.sofa.domain.alarm.enums.AlarmType;
import com.sw19.sofa.domain.alarm.repository.AlarmRepository;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
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
}
