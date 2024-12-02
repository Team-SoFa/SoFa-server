package com.sw19.sofa.domain.alarm.job;

import com.sw19.sofa.domain.alarm.service.AlarmService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredAlarmJob implements Job {
    private AlarmService alarmService;

    @Autowired
    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        alarmService.deleteExpiredAlarm();
    }
}
