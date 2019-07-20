package com.wl.springbatchdemo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/27 0027 21:30
 * @since 1.0.0
 */
@Service
public class PaymentService {

    private final JobLauncher jobLauncher;
    private final Job flowJob;

    @Autowired
    public PaymentService(JobLauncher jobLauncher, Job flowJob) {
        this.jobLauncher = jobLauncher;
        this.flowJob = flowJob;
    }


    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @Bean
    public void run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder().addDate("time", new Date()).toJobParameters();
        jobLauncher.run(flowJob, jobParameters);
    }
}
