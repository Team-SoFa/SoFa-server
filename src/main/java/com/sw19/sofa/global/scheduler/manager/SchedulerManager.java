package com.sw19.sofa.global.scheduler.manager;

import com.sw19.sofa.domain.remind.job.MoveUnusedLinkCardListToRemindJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SchedulerManager {
    private final Scheduler scheduler;

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {
        scheduler.clear();
        scheduler.start();
        scheduleMoveUnusedLinkListToRemindJob();
    }

    private void scheduleMoveUnusedLinkListToRemindJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(MoveUnusedLinkCardListToRemindJob.class)
                .withIdentity("moveUnusedLinkListToRemindJob")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}

