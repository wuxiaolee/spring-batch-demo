package com.wl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Set;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/8 0008 21:08
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JobOperatorTest {

    @Autowired
    private  JobLauncher jobLauncher;
    @Autowired
    private  Job flowJob;
    @Autowired
    private JobOperator jobOperator;

    @Test
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder().addDate("time", new Date()).toJobParameters();
        jobLauncher.run(flowJob, jobParameters);
    }

    @Test
    public void startJob() throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        jobOperator.start("jobOperatorFlowJob", "para="+new Date().toString());
    }

    /**
     * 关闭不是立即发生的，因为没有办法将一个任务立刻强制停掉，尤其是当任务进行到开发人员自己的代码段时，框架在此刻是无能为力的，比如某个业务逻辑处理。
     * 而一旦控制权还给了框架，它会立刻设置当前 StepExecution 为 BachStatus.STOPPED ，意为停止，然后保存，最后在完成前对JobExecution进行相同的操作。
     * @throws NoSuchJobException
     * @throws NoSuchJobExecutionException
     * @throws JobExecutionNotRunningException
     */
    @Test
    public void stopJob() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        Set<Long> executions = jobOperator.getRunningExecutions("jobOperatorFlowJob");
        Boolean bool = jobOperator.stop(executions.iterator().next());

        System.out.println("stop success=" + bool);
    }

    /**
     * 一个job的执行过程当执行到FAILED状态之后，如果它是可重启的，它将会被重启。如果任务的执行过程状态是ABANDONED，那么框架就不会重启它。
     * ABANDONED状态也适用于执行步骤，使得它们可以被跳过，即便是在一个可重启的任务执行之中：如果任务执行过程中碰到在上一次执行失败后标记为ABANDONED的步骤，
     * 将会跳过该步骤直接到下一步(这是由任务流定义和执行步骤的退出码决定的)。如果当前的系统进程死掉了(“kill -9”或系统错误)，job自然也不会运行，
     * 但JobRepository是无法侦测到这个错误的，因为进程死掉之前没有对它进行任何通知。你必须手动的告诉它，你知道任务已经失败了还是说考虑放弃这个任务
     * （设置它的状态为FAILED或ABANDONED）-这是业务逻辑层的事情，无法做到自动决策。只有在不可重启的任务中才需要设置为FAILED状态，
     * 或者你知道重启后数据还是有效的。Spring Batch Admin中有一系列工具JobService，用以取消正在进行执行的任务。
     * Mark the JobExecution as ABANDONED. If a stop signal is ignored
     * because the process died this is the best way to mark a job as finished with (as opposed to STOPPED).
     * An abandoned job execution can be restarted, but a stopping one cannot.
     *
     * throw exception: JobExecutionAlreadyRunningException: JobExecution is running or complete and therefore cannot be aborted
     */
    @Test
    public void abandonJob() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
        JobExecution jobExecution = jobOperator.abandon(230L);

        System.out.println("abandon success,jobExecution=" + jobExecution);
    }

    /**
     * Restart a failed or stopped JobExecution. Fails with an exception if the id provided does not exist or corresponds to a JobInstance
     * that in normal circumstances already completed successfully.
     *
     * Parameters: executionId - the id of a failed or stopped JobExecution
     * Returns: the id of the JobExecution that was started
     *
     * restart ABANDON:JobInstanceAlreadyCompleteException: A job instance already exists and is complete for parameters={para=Wed May 08 23:28:08 CST 2019}.
     *                  If you want to run this job again, change the parameters
     * restart STOPPED:重启成功，但是好像启动了两次 TODO
     *
     * restart FAILED:重启成功，但是好像启动了两次 TODO
     *
     * @throws JobParametersInvalidException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws NoSuchJobExecutionException
     * @throws NoSuchJobException
     */
    @Test
    public void restartJob() throws JobParametersInvalidException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException {
        Long jobExecutionId = jobOperator.restart(598L);
        System.out.println("jobExecutionId=" + jobExecutionId);
    }


}
