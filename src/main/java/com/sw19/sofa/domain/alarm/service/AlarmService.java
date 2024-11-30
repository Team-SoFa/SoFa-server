package com.sw19.sofa.domain.alarm.service;

import com.sw19.sofa.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
}
