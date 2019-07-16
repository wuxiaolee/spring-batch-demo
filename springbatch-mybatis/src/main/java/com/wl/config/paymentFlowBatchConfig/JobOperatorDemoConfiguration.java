package com.wl.config.paymentFlowBatchConfig;

import com.wl.listener.MyJobExecutionListener;
import com.wl.listener.MyStepExecutionListener;
import com.wl.model.PaymentFlowEntity;
import com.wl.model.UserPaymentReportEntity;
import com.wl.processor.DemoProcessor;
import com.wl.reader.DemoReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

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
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry jobRegistry;

    private ApplicationContext context;

    @Autowired
    private DemoReader demoReader;
    @Autowired
    private DemoProcessor demoProcessor;

//    @Bean
//    public Job jobOperatorFlowJob() throws Exception {
//        return jobBuilderFactory.get("jobOperatorFlowJob")
//                .listener(jobListener)
//                .start(jobOperatorPaymentFlowStep())
//                .next(jobOperatorPaymentFlowReportStep())
//                .build();
//    }

    @Bean
    public Job jobOperatorFlowJob() throws Exception {
        return jobBuilderFactory.get("jobOperatorFlowJob")
                .start(jobOperatorPaymentFlowStep())
//                .split(new SimpleAsyncTaskExecutor())
//                .add(reportFlow())
//                .end()
                .build();
    }

    @Bean
    public Flow reportFlow() throws Exception {
        return new FlowBuilder<Flow>("reportFlow")
                .start(jobOperatorPaymentFlowReportStep())
                .build();
    }

    @Bean
    public Step jobOperatorPaymentFlowStep() throws Exception {
        return stepBuilderFactory.get("jobOperatorPaymentFlowStep")
                .listener(stepListener)
                .<List<PaymentFlowEntity>, PaymentFlowEntity>chunk(1)
                .reader(demoReader)
//                .processor(jobOperatorProcessor())
                .processor(demoProcessor)
                .writer(jobOperatorWriter())
                .build();
    }

    @Bean
    public Step jobOperatorPaymentFlowReportStep() throws Exception {
        return stepBuilderFactory.get("jobOperatorPaymentFlowReportStep")
                .listener(stepListener)
                .<UserPaymentReportEntity, UserPaymentReportEntity>chunk(10)
                .reader(jobOperatorReader2())
                .processor(jobOperatorProcessor2())
                .writer(jobOperatorWriter2())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<List<PaymentFlowEntity>> jobOperatorReader() throws Exception {
        MyBatisPagingItemReader<List<PaymentFlowEntity>> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.wl.mapper.PaymentFlowMapper.queryPaymentFlow");
        reader.setPageSize(600);
        System.out.println("reader=" + reader);
        return reader;
    }

    @Bean
    public MyBatisPagingItemReader<UserPaymentReportEntity> jobOperatorReader2() throws Exception {
        MyBatisPagingItemReader<UserPaymentReportEntity> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.wl.mapper.UserPaymentFlowReportMapper.queryUserPaymentFlowReport");
        reader.setPageSize(600);
        System.out.println("reader=" + reader);
        return reader;
    }

    @Bean
    public ItemProcessor<PaymentFlowEntity, PaymentFlowEntity> jobOperatorProcessor() {
        return item -> {
//            if (item.getPaymentFlowId() == 5) {
//                System.out.println(1/0);
//            }
            System.out.println("item=" + item );
            item.setPaymentAmount(new BigDecimal(500));
            return item;
        };
    }

    @Bean
    public ItemProcessor<UserPaymentReportEntity, UserPaymentReportEntity> jobOperatorProcessor2() {
        return item -> {
//            if (item.getId() == 2) {
//                System.out.println(1/0);
//            }
            System.out.println("UserPaymentReportEntity=" + item );
            item.setAmount(new BigDecimal(500));
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
    public MyBatisBatchItemWriter<UserPaymentReportEntity> jobOperatorWriter2() throws Exception {
        MyBatisBatchItemWriter<UserPaymentReportEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.wl.mapper.UserPaymentFlowReportMapper.add");
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
