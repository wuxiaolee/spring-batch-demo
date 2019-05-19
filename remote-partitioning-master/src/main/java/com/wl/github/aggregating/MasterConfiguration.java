package com.wl.github.aggregating;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/19 0019 20:36
 * @since 1.0.0
 */
//@Configuration
//@EnableBatchProcessing
//@EnableBatchIntegration
//@Import(value = {DataSourceConfiguration.class, BrokerConfiguration.class})
public class MasterConfiguration {
//    private static final int GRID_SIZE = 3;
//
//    private final JobBuilderFactory jobBuilderFactory;
//
//    private final RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory;
//
//
//    public MasterConfiguration(JobBuilderFactory jobBuilderFactory,
//                               RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory) {
//
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.masterStepBuilderFactory = masterStepBuilderFactory;
//    }
//
//    /*
//     * Configure outbound flow (requests going to workers)
//     */
//    @Bean
//    public DirectChannel requests() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
//        return IntegrationFlows
//                .from(requests())
//                .handle(Jms.outboundAdapter(connectionFactory).destination("requests"))
//                .get();
//    }
//
//    /*
//     * Configure inbound flow (replies coming from workers)
//     */
//    @Bean
//    public DirectChannel replies() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
//        return IntegrationFlows
//                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("replies"))
//                .channel(replies())
//                .get();
//    }
//
//    /*
//     * Configure the master step
//     */
//    @Bean
//    public Step masterStep() {
//        return this.masterStepBuilderFactory.get("masterStep")
//                .partitioner("workerStep", new BasicPartitioner())
//                .gridSize(GRID_SIZE)
//                .outputChannel(requests())
//                .inputChannel(replies())
//                .build();
//    }
//
//    @Bean
//    public Job remotePartitioningJob() {
//        return this.jobBuilderFactory.get("remotePartitioningJob")
//                .start(masterStep())
//                .build();
//    }

}
