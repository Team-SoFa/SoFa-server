package com.sw19.sofa.global.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StepLoggingListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step '{}' is starting.", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step '{}' has finished with status: {}.",
                stepExecution.getStepName(),
                stepExecution.getStatus());
        if (stepExecution.getStatus().isUnsuccessful()) {
            log.error("Step '{}' failed with read count: {}, write count: {}, commit count: {}",
                    stepExecution.getStepName(),
                    stepExecution.getReadCount(),
                    stepExecution.getWriteCount(),
                    stepExecution.getCommitCount());
        }
        return stepExecution.getExitStatus();
    }
}
