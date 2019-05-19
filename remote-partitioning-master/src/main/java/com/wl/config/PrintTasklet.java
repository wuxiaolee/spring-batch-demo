//package com.wl.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//
///**
// * <>
// *
// * @author wulei
// * @create 2019/5/9 0009 22:59
// * @since 1.0.0
// */
//@Slf4j
////@Component
//public class PrintTasklet implements Tasklet {
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        log.info("has been execute on stepName=" + chunkContext.getStepContext().getStepName() +
//                " has been execute on thread=" + Thread.currentThread().getName());
//        return RepeatStatus.FINISHED;
//    }
//}
