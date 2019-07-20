package com.wl.config.job;

import com.wl.common.Constant;
import com.wl.config.partition.RangePartitioner;
import com.wl.itemoperation.SlaveUserInfoProcessor;
import com.wl.itemoperation.SlaveUserInfoWriter;
import com.wl.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/18 0018 11:12
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class LocalPartitioningJobConfiguration {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "taskExecutor")
    private SimpleAsyncTaskExecutor taskExecutor;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private SlaveUserInfoProcessor slaveUserInfoProcessor;
    @Autowired
    private SlaveUserInfoWriter slaveUserInfoWriter;

    @Bean(name = "localPartitioningJob")
    public Job localPartitioningJob() {
        return jobBuilderFactory
                .get("localPartitioningJob")
                .incrementer(new RunIdIncrementer())
                .start(masterStep())
                .build();
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory
                .get("masterStep")
                .partitioner(slaveStep().getName(), new RangePartitioner(dataSource))
                .step(slaveStep())
                .partitionHandler(masterSlaveHandler())
                .build();
    }

    @Bean(name = "slaveStep")
    public Step slaveStep() {
        log.info("...........called slave .........");

        return stepBuilderFactory
                .get("slaveStep")
                .<UserInfo, UserInfo>chunk(100)
                .reader(slaveReader(null, null, null))
                .processor(slaveUserInfoProcessor)
                .writer(slaveUserInfoWriter)
                .build();
    }

    @Bean
    public PartitionHandler masterSlaveHandler() {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setGridSize(Constant.GRID_SIZE);
        handler.setTaskExecutor(taskExecutor);
        handler.setStep(slaveStep());
        try {
            handler.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler;
    }

    @Bean(name = "taskExecutor")
    public SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<UserInfo> slaveReader(
            @Value("#{stepExecutionContext['minValue']}") Long start,
            @Value("#{stepExecutionContext['maxValue']}") Long end,
            @Value("#{stepExecutionContext['threadName']}") String name) {

        log.info("slaveReader start " + start + " " + end);

        JpaPagingItemReader reader = new JpaPagingItemReader();
        try {
            JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<>();
            String sql = "select * from user_info where user_id >= :start and user_id <= :end";
            queryProvider.setSqlQuery(sql);
            queryProvider.setEntityClass(UserInfo.class);
            reader.setQueryProvider(queryProvider);
            Map queryParames= new HashMap();
            queryParames.put("start",start);
            queryParames.put("end",end);
            reader.setParameterValues(queryParames);
            reader.setEntityManagerFactory(entityManagerFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;

    }


}
