package com.wl;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wulei
 * @date 2019/5/18 0018 10:49
 */
@SpringBootApplication
@EnableBatchProcessing
@EnableBatchIntegration
public class WorkerRemotePartitioningApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerRemotePartitioningApplication.class, args);
    }

}
