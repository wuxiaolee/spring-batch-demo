package com.wl.config.paymentFlowBatchConfig.jobDecisionDemoConfiguration;

import com.wl.config.PrintTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Decision>
 *
 * @author wulei
 * @create 2019/5/9 0009 22:46
 * @since 1.0.0
 */
@Configuration
public class JobDecisionDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private PrintTasklet printTasklet;
    @Autowired
    @Qualifier(value = "customJobDecider")
    private JobExecutionDecider customJobDecider;

    /**
     * Job
     * @return
     */
    @Bean
    public Job jobDecisionDemoJob() {
        return jobBuilderFactory.get("jobDecisionDemoJob")
                .start(jobDecisionDemoStep1())
                .next(customJobDecider)
                .from(customJobDecider).on("SECOND").to(jobDecisionDemoStep2())
                .from(customJobDecider).on("THIRD").to(jobDecisionDemoStep3())
                .from(jobDecisionDemoStep3()).on("*").to(customJobDecider)
                .end()
                .build();
    }

    @Bean
    public Step jobDecisionDemoStep1() {
        return stepBuilderFactory.get("jobDecisionDemoStep1").tasklet(printTasklet).build();
    }

    @Bean
    public Step jobDecisionDemoStep2() {
        return stepBuilderFactory.get("jobDecisionDemoStep2").tasklet(printTasklet).build();
    }

    @Bean
    public Step jobDecisionDemoStep3() {
        return stepBuilderFactory.get("jobDecisionDemoStep3").tasklet(printTasklet).build();
    }


}
