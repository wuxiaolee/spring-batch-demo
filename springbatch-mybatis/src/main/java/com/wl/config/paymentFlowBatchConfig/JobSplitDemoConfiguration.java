package com.wl.config.paymentFlowBatchConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * <Job Split>
 *
 * @author wulei
 * @create 2019/5/9 0009 22:05
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class JobSplitDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 异步执行Flow;add() can add more flow
     * @return
     */
    @Bean
    public Job jobSplitDemoJob() {
        return jobBuilderFactory.get("jobSplitDemoJob")
                .start(jobSplitDemoFlow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(jobSplitDemoFlow2())
                .end()
                .build();
    }

    /**
     * jobSplitDemoFlow1
     * @return
     */
    @Bean
    public Flow jobSplitDemoFlow1() {
        return new FlowBuilder<Flow>("jobSplitDemoFlow1")
                .start(jobSplitDemoStep1())
                .next(jobSplitDemoStep2())
                .build();
    }

    /**
     * jobSplitDemoFlow2
     * @return
     */
    @Bean
    public Flow jobSplitDemoFlow2() {
        return new FlowBuilder<Flow>("jobSplitDemoFlow2")
                .start(jobSplitDemoStep3())
                .next(jobSplitDemoStep4())
                .build();
    }

    @Bean
    public Step jobSplitDemoStep1() {
        return stepBuilderFactory.get("jobSplitDemoStep1").tasklet(tasklet()).build();
    }

    @Bean
    public Step jobSplitDemoStep2() {
        return stepBuilderFactory.get("jobSplitDemoStep2").tasklet(tasklet()).build();
    }

    @Bean
    public Step jobSplitDemoStep3() {
        return stepBuilderFactory.get("jobSplitDemoStep3").tasklet(tasklet()).build();
    }

    @Bean
    public Step jobSplitDemoStep4() {
        return stepBuilderFactory.get("jobSplitDemoStep4").tasklet(tasklet()).build();
    }

    private Tasklet tasklet() {
        return new PrintTasklet();
    }
}

@Slf4j
class PrintTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("has been execute on stepName=" + chunkContext.getStepContext().getStepName() +
                " has been execute on thread=" + Thread.currentThread().getName());
        return RepeatStatus.FINISHED;
    }
}
