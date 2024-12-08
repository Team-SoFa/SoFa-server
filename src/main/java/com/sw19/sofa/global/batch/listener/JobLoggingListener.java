package com.sw19.sofa.global.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobLoggingListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job '{}' is starting.", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job '{}' has finished with status: {}.",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());
        if (jobExecution.getStatus().isUnsuccessful()) {
            log.error("Job '{}' failed with exit description: {}",
                    jobExecution.getJobInstance().getJobName(),
                    jobExecution.getExitStatus().getExitDescription());
        }
    }
}