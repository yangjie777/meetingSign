package com.jmpt.yhn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * Created by yhn on 2017/9/30.
 */
@Configuration
@EnableScheduling
public class QuartzConfigration {
    @Autowired
    private MyJobFactory myJobFactory;
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(myJobFactory);
        return factory;
    }

}

