package com.wl.config.paymentFlowBatchConfig.jobDecisionDemoConfiguration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

/**
 * <分析结果：首先job中先运行firstStep(),然后进入到myDecider中Count++,此时count=1，返回”ODD”,
 * job中执行oddStep(),然后无论什么状态再次进入myDecider中，此时count=2,故返回”EVEN”，下一步执行evenStep();>
 *
 * @author wulei
 * @create 2019/5/9 0009 22:49
 * @since 1.0.0
 */
@Component(value = "customJobDecider")
public class CustomJobDecider implements JobExecutionDecider {
    private int count = 0;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        if (count % 2 == 0) {
            return new FlowExecutionStatus("SECOND");
        } else {
            return new FlowExecutionStatus("THIRD");
        }
    }
}
