package com.wl.springbatchdemo.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * <Step拦截器>
 *
 * @author wulei
 * @create 2019/4/29 0029 22:28
 * @since 1.0.0
 */
@Component
public class MyStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("执行开始");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("执行结束");
        return ExitStatus.COMPLETED;
    }
}
