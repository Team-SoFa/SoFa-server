package com.sw19.sofa.global.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import static com.sw19.sofa.global.common.constants.Constants.*;

@Configuration
public class BatchConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(batchConcurrencyLimit); // 동시에 실행할 작업의 수
        return asyncTaskExecutor;
    }
}
