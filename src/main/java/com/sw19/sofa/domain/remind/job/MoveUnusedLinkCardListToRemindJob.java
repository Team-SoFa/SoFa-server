package com.sw19.sofa.domain.remind.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import static com.sw19.sofa.global.common.constants.Constants.moveRemind;

@Component
@Slf4j
public class MoveUnusedLinkCardListToRemindJob extends QuartzJobBean {
    private Job remindJob;
    private JobLauncher jobLauncher;

    @Autowired
    public void setRemindJob(@Qualifier(moveRemind + "Job") Job remindJob) {
        this.remindJob = remindJob;
    }

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Override
    public void executeInternal(JobExecutionContext context) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("curTime", System.currentTimeMillis()) // 고유한 파라미터 추가
                    .toJobParameters();
            jobLauncher.run(remindJob, jobParameters);
            log.info("Remind Job executed successfully.");
        } catch (Exception e) {
            log.error("Error executing Remind Job", e);
        }
    }
}
