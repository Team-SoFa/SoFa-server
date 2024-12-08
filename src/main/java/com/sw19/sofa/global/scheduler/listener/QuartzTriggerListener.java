package com.sw19.sofa.global.scheduler.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return "globalTrigger";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("[Trigger 실행] triggerKey: {}", trigger.getKey());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("[Trigger 중단 체크] triggerKey: {}", trigger.getKey());
        return false; // 항상 실행
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.warn("[Trigger 실행 실패] triggerKey: {}", trigger.getKey());
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("[Trigger 완료] triggerKey: {}", trigger.getKey());
    }
}
