package com.wl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/18 0018 10:49
 * @since 1.0.0
 */
@Configuration
public class BeanConfiguration {

    @Bean(name = "taskExecutor")
    public SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
