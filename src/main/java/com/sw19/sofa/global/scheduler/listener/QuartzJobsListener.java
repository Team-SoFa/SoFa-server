package com.sw19.sofa.global.scheduler.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class QuartzJobsListener implements JobListener {

    @Override
    public String getName() {
        return "globalJob";
    }

    /**
     * Job 수행 전
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("[Job 실행 시작] jobKey: {}", jobKey);
    }

    /**
     * Job 중단 상태
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobKey jobKey = context.getJobDetail().getKey();
        log.warn("[Job 실행 중단됨] jobKey: {}", jobKey);
    }

    /**
     * Job 수행 완료 후
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("[Job 실행 완료] jobKey: {}", jobKey);

        try {
            handleJobCompletion(context, jobException);
        } catch (SchedulerException e) {
            log.error("[Job 처리 실패] jobKey: {}, Exception: {}", jobKey, e.getMessage());
        }
    }

    /**
     * Job 완료 후 처리 로직
     */
    private void handleJobCompletion(JobExecutionContext context, JobExecutionException jobException) throws SchedulerException {
        final int maxRetryCount = 3;

        JobKey jobKey = context.getJobDetail().getKey();
        Scheduler scheduler = context.getScheduler();
        JobDataMap jobDataMap = context.getTrigger().getJobDataMap();

        // 실패 횟수 및 상태
        int failCount = 0;

        if (jobDataMap.containsKey("failCnt")) {
            try {
                failCount = Integer.parseInt(jobDataMap.getString("failCnt"));
            } catch (NumberFormatException e) {
                log.warn("[JobDataMap 변환 실패] failCnt 값을 Integer로 변환할 수 없습니다. 기본값(0)으로 설정합니다.", e);
            }
        }

        boolean stopJob = "Y".equals(jobDataMap.getString("stop"));
        boolean retryEnabled = "Y".equals(jobDataMap.getString("retry"));

        log.debug("Job 상태 - failCount: {}, stopJob: {}, retryEnabled: {}", failCount, stopJob, retryEnabled);

        if (jobException != null) {
            log.warn("[Job 실패] jobKey: {}, Exception: {}", jobKey, jobException.getMessage());

            if (!retryEnabled) {
                // 재시도 불가, 트리거 중단
                scheduler.pauseJob(jobKey);
            } else {
                // 재시도 처리
                failCount++;
                if (failCount >= maxRetryCount) {
                    jobDataMap.put("stop", "Y");
                } else {
                    jobDataMap.put("failCnt", failCount);
                }

                if (!stopJob) {
                    rescheduleJob(context);
                } else {
                    scheduler.pauseJob(jobKey);
                }
            }

            log.info("[Job 실패 알림] jobKey: {}", jobKey);
        }
    }

    /**
     * Job 재스케줄링
     */
    private void rescheduleJob(JobExecutionContext context) throws SchedulerException {
        Trigger oldTrigger = context.getTrigger();
        CronTrigger cronTrigger = (CronTrigger) oldTrigger;

        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(oldTrigger.getKey())
                .startAt(new Date(System.currentTimeMillis() + 60000)) // 1분 후 실행
                .withSchedule(cronTrigger.getScheduleBuilder())
                .usingJobData(oldTrigger.getJobDataMap()) // 데이터 유지
                .build();

        context.getScheduler().rescheduleJob(oldTrigger.getKey(), newTrigger);
        log.info("[Job 재스케줄링 완료] triggerKey: {}", oldTrigger.getKey());
    }
}

