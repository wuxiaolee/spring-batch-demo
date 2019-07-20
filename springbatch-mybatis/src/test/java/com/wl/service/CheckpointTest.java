package com.wl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/8 0008 21:08
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest

@ImportResource(locations= {"classpath:myJob.xml"})
public class CheckpointTest {

    @Autowired
    private  JobLauncher jobLauncher;
    @Autowired
    private JobOperator jobOperator;


    @Test
    public void startJob() throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        jobOperator.start("myJob", new Date().toString());
    }


}
