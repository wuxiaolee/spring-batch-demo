package com.wl.config.job;

import com.wl.config.partition.BasicPartitioner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.partition.RemotePartitioningMasterStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;

import javax.sql.DataSource;

import static com.wl.common.Constant.GRID_SIZE;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/18 0018 11:12
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class MasterJobConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory;

    @Autowired
    @Qualifier(value = "requests")
    private DirectChannel requests;
    @Autowired
    @Qualifier(value = "replies")
    private DirectChannel replies;

    @Bean(name = "remotePartitioningJob")
    public Job remotePartitioningJob() {
        return jobBuilderFactory
                .get("remotePartitioningJob")
                .start(masterStep())
                .build();
    }
    /**
     * Configure the master step
     */
    @Bean
    public Step masterStep() {
        return this.masterStepBuilderFactory.get("masterStep")
                .partitioner("workerStep", new BasicPartitioner())
                .gridSize(GRID_SIZE)
                .outputChannel(requests)
                .inputChannel(replies)
                .build();
    }


}
