package com.wl.springbatchdemo.config;

import com.wl.springbatchdemo.entity.PaymentFlow;
import com.wl.springbatchdemo.entity.UserPaymentReport;
import com.wl.springbatchdemo.listener.MyJobExecutionListener;
import com.wl.springbatchdemo.listener.MyStepExecutionListener;
import com.wl.springbatchdemo.repo.UserPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/27 0027 14:25
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class PaymentFlowBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final UserPaymentRepository userPaymentRepository;
    private final MyJobExecutionListener jobListener;
    private final MyStepExecutionListener stepListener;


    @Autowired
    public PaymentFlowBatchConfig(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  EntityManagerFactory entityManagerFactory,
                                  UserPaymentRepository userPaymentRepository,
                                  MyJobExecutionListener jobListener,
                                  MyStepExecutionListener stepListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.userPaymentRepository = userPaymentRepository;
        this.jobListener = jobListener;
        this.stepListener = stepListener;
    }

    @Bean
    public Step statisticsPaymentFlowStep() {
        return stepBuilderFactory.get("statisticsPaymentFlowStep")
                .listener(stepListener)
                .startLimit(1)
                .<PaymentFlow, UserPaymentReport>chunk(10)
                .reader(jpaItemReader())
                .processor(processor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public Job flowJob(Step statisticsPaymentFlowStep) {
        return jobBuilderFactory.get("statisticsPaymentFlowJob")
                .listener(jobListener)
                .flow(statisticsPaymentFlowStep)
                .on("COMPLETED").end()
                .end()
                .build();
    }


    @Bean
    public JpaPagingItemReader<PaymentFlow> jpaItemReader() {
        JpaPagingItemReader reader = new JpaPagingItemReader();
        String sqlQuery = "select * from payment_flow";
        try {
            JpaNativeQueryProvider<PaymentFlow> queryProvider = new JpaNativeQueryProvider<PaymentFlow>();
            queryProvider.setSqlQuery(sqlQuery);
            queryProvider.setEntityClass(PaymentFlow.class);
            queryProvider.afterPropertiesSet();
            reader.setEntityManagerFactory(entityManagerFactory);
            reader.setPageSize(100);
            reader.setQueryProvider(queryProvider);
            reader.afterPropertiesSet();
            reader.setSaveState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;

    }

    @Bean
    public ItemProcessor<PaymentFlow, UserPaymentReport> processor() {
        Map<Long, UserPaymentReport> map = new HashMap<>(1000);
        return item -> {
            UserPaymentReport userPaymentReport;
            if (map.containsKey(item.getUserId())) {
                userPaymentReport = map.get(item.getUserId());
                userPaymentReport.setAmount(userPaymentReport.getAmount().add(item.getPaymentAmount()));
                userPaymentReport.setStatisticsDate(new Date());
                map.put(item.getUserId(), userPaymentReport);
            } else {
                userPaymentReport = new UserPaymentReport();
                userPaymentReport.setUserId(item.getUserId());
                userPaymentReport.setStatisticsDate(new Date());
                userPaymentReport.setAmount(item.getPaymentAmount());

                map.put(item.getUserId(), userPaymentReport);
            }
            //还是需要借助于ItemWriter
            userPaymentRepository.save(userPaymentReport);
            return userPaymentReport;

        };
    }

    @Bean
    public ItemWriter<UserPaymentReport> jpaItemWriter() {
        JpaItemWriter<UserPaymentReport> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }




}
