package com.wl.config.paymentFlowBatchConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wl.listener.MyJobExecutionListener;
import com.wl.listener.MyStepExecutionListener;
import com.wl.model.PaymentFlowEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/23 0023 21:16
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class FromDbToFileJobConfiguration implements ApplicationContextAware {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private MyJobExecutionListener jobListener;
    @Autowired
    private MyStepExecutionListener stepListener;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry jobRegistry;

    private ApplicationContext context;

    private int count = 0;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Bean
    public Job createFileJob2() throws Exception {
        return jobBuilderFactory.get("createFileJob2")
                .start(createFileStep2())
                .build();
    }

    @Bean
    public Step createFileStep2() throws Exception {
        return stepBuilderFactory.get("createFileStep2")
                .<PaymentFlowEntity, PaymentFlowEntity>chunk(5)
                .reader(createXmlFileReader())
                .processor(createXmlFileProcessor())
//                .writer(createXmlFileWriter())
                .writer(dbFileWriter())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<PaymentFlowEntity> createXmlFileReader() throws Exception {
        MyBatisPagingItemReader<PaymentFlowEntity> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.wl.mapper.PaymentFlowMapper.queryPaymentFlow");
        reader.setPageSize(1);
        System.out.println("reader=" + reader);

//        count++;
//        System.out.println("count1=" + count);
//
//        if (count == 8) {
//            System.out.println(1/0);
//        }

        return reader;
    }

    @Bean
    public ItemProcessor<PaymentFlowEntity, PaymentFlowEntity> createXmlFileProcessor() {
        return item -> {
            System.out.println("processor=" + item );
            item.setPaymentAmount(new BigDecimal(600));

//            if (item.getPaymentFlowId() == 8) {
//                throw new RuntimeException("抛出异常");
//            }

            return item;
        };
    }

    @Bean
    public StaxEventItemWriter<PaymentFlowEntity> createXmlFileWriter() throws Exception {

        StaxEventItemWriter<PaymentFlowEntity> writer = new StaxEventItemWriter<>();

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>(20);
        aliases.put("paymentFlow", PaymentFlowEntity.class);
        marshaller.setAliases(aliases);

        System.out.println("marshaller" + marshaller);

        writer.setRootTagName("paymentFlows");
        writer.setMarshaller(marshaller);

        count++;
        System.out.println("count1=" + count);

        if (count == 2) {
            System.out.println(1/0);
        }

        String path = "e:\\paymentFlow.xml";
        writer.setResource(new FileSystemResource(path));
        writer.afterPropertiesSet();

        return writer;
    }


    @Bean
    public FlatFileItemWriter<PaymentFlowEntity> dbFileWriter() throws Exception
    {
        FlatFileItemWriter<PaymentFlowEntity> writer=new FlatFileItemWriter<PaymentFlowEntity>();
        String path = "e:\\paymentFlow.txt";
        writer.setResource(new FileSystemResource(path));

        //把一个 Customer对象转成一行字符串
        writer.setLineAggregator(new LineAggregator<PaymentFlowEntity>() {

            ObjectMapper mapper = new ObjectMapper();

            @Override
            public String aggregate(PaymentFlowEntity item) {
                String str = null;
                try {

                    System.out.println("writer=" + item);
                    str=mapper.writeValueAsString(item);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                if (item.getPaymentFlowId() == 8) {
                    throw new RuntimeException("throw exception");
                }
                return str;
            }
        });
        writer.afterPropertiesSet();
        return writer;
    }



    @Bean
    public JobOperator createXmlFileJobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        //注册job字符串
        operator.setJobRegistry(jobRegistry);

        return operator;
    }

}



































































