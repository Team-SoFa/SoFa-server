package com.sw19.sofa.domain.recycleBin.job;

import com.sw19.sofa.domain.recycleBin.service.RecycleBinManageService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredLinkCardListInRecycleBinJob extends QuartzJobBean {
    private RecycleBinManageService recycleBinManageService;

    @Autowired
    public void setRecycleBinManageService(RecycleBinManageService recycleBinManageService) {
        this.recycleBinManageService = recycleBinManageService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        recycleBinManageService.deleteExpiredLinkCardList();
    }
}
