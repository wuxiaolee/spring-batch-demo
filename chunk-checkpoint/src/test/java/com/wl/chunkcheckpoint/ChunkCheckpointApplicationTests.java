package com.wl.chunkcheckpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChunkCheckpointApplicationTests {

    @Autowired
    private org.springframework.batch.core.launch.JobOperator jobOperator;


    @Test
    public void contextLoads() throws InterruptedException, JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
//        JobOperator jobOperator = getJobOperator();
        Long executionId = jobOperator.start("myJob", new Date().toString());
        System.out.println(executionId + "=executionId");
//        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
//
//        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
//
//        for (StepExecution stepExecution : jobOperator.getStepExecutions(executionId)) {
//            if (stepExecution.getStepName().equals("myStep")) {
//                Map<Metric.MetricType, Long> metricsMap = getMetricsMap(stepExecution.getMetrics());
//
//                // <1> The read count should be 10 elements. Check +MyItemReader+.
//                assertEquals(10L, metricsMap.get(READ_COUNT).longValue());
//
//                // <2> The write count should be 5. Only half of the elements read are processed to be written.
//                assertEquals(10L / 2L, metricsMap.get(WRITE_COUNT).longValue());
//
//                // <3> The commit count should be 3. Checkpoint is on every 5th read, plus one final read-commit.
//                assertEquals(10L / 5L + 1, metricsMap.get(COMMIT_COUNT).longValue());
//            }
//        }
//
//        // <4> The checkpoint algorithm should be checked 10 times. One for each element read.
//        assertTrue(checkpointCountDownLatch.await(0, SECONDS));
//
//        // <5> Job should be completed.
//        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
//    }
    }



}
