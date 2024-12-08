package com.sw19.sofa.domain.alarm.job;

import com.sw19.sofa.domain.alarm.service.AlarmService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredAlarmJob extends QuartzJobBean {
    private AlarmService alarmService;

    @Autowired
    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }
    @Override
    protected void executeInternal(JobExecutionContext context) {
        alarmService.deleteExpiredAlarm();
    }
}
