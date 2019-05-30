package com.wl.config.paymentFlowBatchConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Job flow>
 *
 * @author wulei
 * @create 2019/5/9 0009 21:26
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class JobFlowDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 创建Job执行FLow & Step
     *
     * @return
     */
    @Bean
    public Job jobFlowDemoJob() {
        return jobBuilderFactory.get("jobFlowDemoJob")
                .start(jobFlowDemoFlow())
                .next(jobFlowDemoStep3())
                .end()
                .build();
    }

    /**
     * 创建Flow:Step的集合
     *
     * @return
     */
    @Bean
    public Flow jobFlowDemoFlow() {
        return new FlowBuilder<Flow>("jobFlowDemoFlow")
                .start(jobFlowDemoStep1())
                .next(jobFlowDemoStep2())
                .build();
    }

    @Bean
    public Step jobFlowDemoStep1() {
        return stepBuilderFactory.get("jobFlowDemoStep1").tasklet((contribution, chunkContext) -> {
            log.info("jobFlowStep1");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step jobFlowDemoStep2() {
        return stepBuilderFactory.get("jobFlowDemoStep2").tasklet(((contribution, chunkContext) -> {
            log.info("jobFlowStep2");
            return RepeatStatus.FINISHED;
        })).build();
    }

    @Bean
    public Step jobFlowDemoStep3() {
//        System.out.println(1/0);
        return stepBuilderFactory.get("jobFlowDemoStep3").tasklet(((contribution, chunkContext) -> {
            log.info("jobFlowStep3");
            return RepeatStatus.FINISHED;
        })).build();
    }


}

















































