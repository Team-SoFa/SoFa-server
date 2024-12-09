package com.sw19.sofa.global.scheduler.config;

import com.sw19.sofa.global.scheduler.listener.QuartzJobsListener;
import com.sw19.sofa.global.scheduler.listener.QuartzTriggerListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final ApplicationContext applicationContext;
    private final QuartzJobsListener quartzJobsListener;
    private final QuartzTriggerListener quartzTriggerListener;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        factory.setGlobalJobListeners(quartzJobsListener);
        factory.setGlobalTriggerListeners(quartzTriggerListener);

        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);

        factory.setTransactionManager(platformTransactionManager);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);

        factory.setQuartzProperties(quartzProperties());

        return factory;
    }

    private Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        Properties properties = null;

        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}