package com.sw19.sofa.global.scheduler.manager;

import com.sw19.sofa.domain.alarm.job.DeleteExpiredAlarmJob;
import com.sw19.sofa.domain.recycleBin.job.DeleteExpiredLinkCardListInRecycleBinJob;
import com.sw19.sofa.domain.remind.job.MemberRemindNotifyJob;
import com.sw19.sofa.domain.remind.job.MoveUnusedLinkCardListToRemindJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import static com.sw19.sofa.global.common.constants.Constants.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerManager {
    private final Scheduler scheduler;

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {
        scheduler.clear();
        scheduler.start();
        scheduleMoveUnusedLinkListToRemindJob();
        scheduleDeleteExpiredLinkCardListInRecycleBinJob();
        deleteExpiredAlarmJob();
    }

    private void scheduleMoveUnusedLinkListToRemindJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(MoveUnusedLinkCardListToRemindJob.class)
                .withIdentity(moveRemind)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();

        scheduler.scheduleJob(job, trigger);
    }



    private void scheduleDeleteExpiredLinkCardListInRecycleBinJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(DeleteExpiredLinkCardListInRecycleBinJob.class)
                .withIdentity(deleteRecycleBin)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public void startMemberRemindNotifyScheduler(String encryptMemberId) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(encryptMemberId, remindNotify);

        // 기존 Job 중복 방지
        if (scheduler.checkExists(jobKey)) {
            return;
        }

        // Job 생성
        JobDetail job = JobBuilder.newJob(MemberRemindNotifyJob.class)
                .withIdentity(jobKey)
                .build();

        // Trigger 생성: 30일마다 실행
        TriggerKey triggerKey = TriggerKey.triggerKey(encryptMemberId, remindNotify);
        SimpleScheduleBuilder thirtyDaysScheduler = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(30L * 24 * 60 * 60 * 1000) // 30일
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(thirtyDaysScheduler)
                .build();

        scheduler.scheduleJob(job, trigger);
    }

    public void stopMemberNotifyScheduler(String encryptMemberId){
        try {
            JobKey jobKey = JobKey.jobKey(encryptMemberId, remindNotify);

            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        }catch (SchedulerException e){
            log.error("Scheduler Error ",e);
        }

    }

    private void deleteExpiredAlarmJob() throws SchedulerException{
        JobDetail job = JobBuilder.newJob(DeleteExpiredAlarmJob.class)
                .withIdentity(deleteExpiredAlarm)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}

