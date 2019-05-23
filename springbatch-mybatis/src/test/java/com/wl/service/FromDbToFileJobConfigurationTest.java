package com.wl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FromDbToFileJobConfigurationTest {

    @Autowired
    private JobOperator createXmlFileJobOperator;

    @Test
    public void startTest() throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        createXmlFileJobOperator.start("createFileJob2", new Date().toString());
    }

    @Test
    public void restartTest() throws JobParametersInvalidException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException {
        createXmlFileJobOperator.restart(397L);
    }
}