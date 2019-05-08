package com.wl.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * <自定义Job执行监听器>
 *
 * @author wulei
 * @create 2019/4/29 0029 22:12
 * @since 1.0.0
 */
@Component
public class MyJobExecutionListener implements JobExecutionListener {

    private Long time;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        time = System.currentTimeMillis();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(String.format("任务耗时：%sms", System.currentTimeMillis() - time));
    }
}
