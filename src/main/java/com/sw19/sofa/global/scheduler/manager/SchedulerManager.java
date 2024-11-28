package com.sw19.sofa.global.scheduler.manager;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SchedulerManager {
    private final Scheduler scheduler;

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {
        scheduler.clear();
    }
}

