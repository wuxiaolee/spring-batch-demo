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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.math.BigDecimal;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/27 0027 14:25
 * @since 1.0.0
 */
@Slf4j
@Configuration
@MapperScan("com.wl.mapper")
public class PaymentFlowBatchConfig {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MyJobExecutionListener jobListener;
    private final MyStepExecutionListener stepListener;

    @Autowired
    private UserPaymentFlowReportMapper userPaymentFlowReportMapper;

    @Autowired
    public PaymentFlowBatchConfig(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  MyJobExecutionListener jobListener,
                                  MyStepExecutionListener stepListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobListener = jobListener;
        this.stepListener = stepListener;
    }

    @Bean
    public Step statisticsPaymentFlowStep() throws Exception {
        return stepBuilderFactory.get("statisticsPaymentFlowStep")
                .listener(stepListener)
                .<PaymentFlowEntity, PaymentFlowEntity>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job flowJob(Step statisticsPaymentFlowStep) {
        return jobBuilderFactory.get("statisticsPaymentFlowJob")
                .listener(jobListener)
                .flow(statisticsPaymentFlowStep)
                .end()
                .build();
    }


    @Bean
    public MyBatisPagingItemReader<PaymentFlowEntity> reader() throws Exception {
        MyBatisPagingItemReader<PaymentFlowEntity> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.wl.mapper.PaymentFlowMapper.queryPaymentFlow");
        reader.setPageSize(600);
        System.out.println("reader=" + reader);
        return reader;
    }


    @Bean
    public ItemProcessor<PaymentFlowEntity, PaymentFlowEntity> processor() {
        return item -> {
            System.out.println("item=" + item );
            item.setPaymentAmount(new BigDecimal(500));
            return item;
        };
    }

    @Bean
    public MyBatisBatchItemWriter<PaymentFlowEntity> writer() throws Exception {
        MyBatisBatchItemWriter<PaymentFlowEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.wl.mapper.PaymentFlowMapper.updatePaymentFlow");
        System.out.println("writer=" + writer);
        return writer;
    }

    //    @Bean
//    public ItemProcessor<PaymentFlowEntity, UserPaymentReportEntity> processor() {
//        Map<Long, UserPaymentReportEntity> map = new HashMap<>(1000);
//        return item -> {
//            UserPaymentReportEntity userPaymentReportEntity;
//            if (map.containsKey(item.getUserId())) {
//                userPaymentReportEntity = map.get(item.getUserId());
//                userPaymentReportEntity.setAmount(userPaymentReportEntity.getAmount().add(item.getPaymentAmount()));
//                userPaymentReportEntity.setStatisticsDate(new Date());
//                map.put(item.getUserId(), userPaymentReportEntity);
//            } else { userPaymentReportEntity = new UserPaymentReportEntity();
//                userPaymentReportEntity.setUserId(item.getUserId());
//                userPaymentReportEntity.setStatisticsDate(new Date());
//                userPaymentReportEntity.setAmount(item.getPaymentAmount());
//                System.out.println(12313131);
//                map.put(item.getUserId(), userPaymentReportEntity);
//            }
//
//            return userPaymentReportEntity;
//
//        };
//    }

//    @Bean
//    public MyBatisBatchItemWriter<UserPaymentReportEntity> writer() throws Exception {
//        MyBatisBatchItemWriter<UserPaymentReportEntity> writer = new MyBatisBatchItemWriter<>();
//        writer.setSqlSessionFactory(sqlSessionFactory());
//        writer.setStatementId("com.wl.mapper.UserPaymentFlowReportMap.add");
//        return writer;
//    }

//    @Bean
//    @Qualifier("jdbcBatchItemWriter")
//    public JdbcBatchItemWriter<UserPaymentReportEntity> jdbcBatchItemWriter() {
//        JdbcBatchItemWriter<UserPaymentReportEntity> writer = new JdbcBatchItemWriter<>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        String sql = "insert into user_payment_report(user_id, statistics_date, amount) values (:userId, :statisticsDate, :amount)";
//        writer.setSql(sql);
//        writer.setDataSource(dataSource);
//        return writer;
//    }
//
//    @Bean
//    @Qualifier("repositoryItemWriter")
//    public RepositoryItemWriter<People> repositoryItemWriter() {
//        RepositoryItemWriter<People> peopleRepositoryItemWriter = new RepositoryItemWriter<>();
//        peopleRepositoryItemWriter.setRepository(peopleCrudRepository);
//        peopleRepositoryItemWriter.setMethodName("save");
//        return peopleRepositoryItemWriter;
//    }

}
