package com.sw19.sofa.domain.recycleBin.job;

import com.sw19.sofa.domain.recycleBin.service.RecycleBinManageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredLinkCardListInRecycleBinJob implements Job {
    private RecycleBinManageService recycleBinManageService;

    @Autowired
    public void setRecycleBinManageService(RecycleBinManageService recycleBinManageService) {
        this.recycleBinManageService = recycleBinManageService;
    }


    @Override
    public void execute(JobExecutionContext context) {
        recycleBinManageService.deleteExpiredLinkCardList();
    }
}
