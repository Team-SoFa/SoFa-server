package com.sw19.sofa.domain.remind.job;

import com.sw19.sofa.domain.remind.service.RemindManageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoveUnusedLinkCardListToRemindJob implements Job {
    private RemindManageService remindManageService;
    @Autowired
    public void setRemindManageService(RemindManageService remindManageService) {
        this.remindManageService = remindManageService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        remindManageService.moveUnusedLinkListToRemind();
    }
}
