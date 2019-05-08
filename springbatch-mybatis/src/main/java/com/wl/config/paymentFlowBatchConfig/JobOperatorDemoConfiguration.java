package com.wl.config.paymentFlowBatchConfig;

import com.wl.listener.MyJobExecutionListener;
import com.wl.listener.MyStepExecutionListener;
import com.wl.mapper.UserPaymentFlowReportMapper;
import com.wl.model.PaymentFlowEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/8 0008 22:44
 * @since 1.0.0
 */
@Slf4j
@Configuration
@MapperScan("com.wl.mapper")
public class JobOperatorDemoConfiguration implements ApplicationContextAware{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private  JobBuilderFactory jobBuilderFactory;
    @Autowired
    private  StepBuilderFactory stepBuilderFactory;
    @Autowired
    private  MyJobExecutionListener jobListener;
    @Autowired
    private  MyStepExecutionListener stepListener;

    @Autowired
    private UserPaymentFlowReportMapper userPaymentFlowReportMapper;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry jobRegistry;

    private Map<String, JobParameter> parameter;

    private ApplicationContext context;



    @Bean
    public Job jobOperatorFlowJob(Step statisticsPaymentFlowStep) {
        return jobBuilderFactory.get("jobOperatorFlowJob")
                .listener(jobListener)
                .flow(statisticsPaymentFlowStep)
                .end()
                .build();
    }

    @Bean
    public Step jobOperatorPaymentFlowStep() throws Exception {
        return stepBuilderFactory.get("jobOperatorPaymentFlowStep")
                .listener(stepListener)
                .<PaymentFlowEntity, PaymentFlowEntity>chunk(1)
                .reader(jobOperatorReader())
                .processor(jobOperatorProcessor())
                .writer(jobOperatorWriter())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<PaymentFlowEntity> jobOperatorReader() throws Exception {
        MyBatisPagingItemReader<PaymentFlowEntity> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.wl.mapper.PaymentFlowMapper.queryPaymentFlow");
        reader.setPageSize(600);
        System.out.println("reader=" + reader);
        return reader;
    }

    @Bean
    public ItemProcessor<PaymentFlowEntity, PaymentFlowEntity> jobOperatorProcessor() {
        return item -> {
            System.out.println("item=" + item );
            item.setPaymentAmount(new BigDecimal(500));
            return item;
        };
    }

    @Bean
    public MyBatisBatchItemWriter<PaymentFlowEntity> jobOperatorWriter() throws Exception {
        MyBatisBatchItemWriter<PaymentFlowEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.wl.mapper.PaymentFlowMapper.updatePaymentFlow");
        System.out.println("writer=" + writer);
        return writer;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistrar() throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();

        postProcessor.setJobRegistry(jobRegistry);
        postProcessor.setBeanFactory(context.getAutowireCapableBeanFactory());
        postProcessor.afterPropertiesSet();

        return postProcessor;
    }

    @Bean
    public JobOperator jobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        //注册job字符串
        operator.setJobRegistry(jobRegistry);

        return operator;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }


}
