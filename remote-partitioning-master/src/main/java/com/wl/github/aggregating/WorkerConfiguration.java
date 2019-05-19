package com.wl.github.aggregating;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/19 0019 20:37
 * @since 1.0.0
 */
//@Configuration
//@EnableBatchProcessing
//@EnableBatchIntegration
//@Import(value = {DataSourceConfiguration.class, BrokerConfiguration.class})
public class WorkerConfiguration {
//    private final RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory;
//
//
//    public WorkerConfiguration(RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory) {
//        this.workerStepBuilderFactory = workerStepBuilderFactory;
//    }
//
//    /*
//     * Configure inbound flow (requests coming from the master)
//     */
//    @Bean
//    public DirectChannel requests() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
//        return IntegrationFlows
//                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
//                .channel(requests())
//                .get();
//    }
//
//    /*
//     * Configure outbound flow (replies going to the master)
//     */
//    @Bean
//    public DirectChannel replies() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
//        return IntegrationFlows
//                .from(replies())
//                .handle(Jms.outboundAdapter(connectionFactory).destination("replies"))
//                .get();
//    }
//
//    /*
//     * Configure the worker step
//     */
//    @Bean
//    public Step workerStep() {
//        return this.workerStepBuilderFactory.get("workerStep")
//                .inputChannel(requests())
//                .outputChannel(replies())
//                .tasklet(tasklet(null))
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public Tasklet tasklet(@Value("#{stepExecutionContext['partition']}") String partition) {
//        return (contribution, chunkContext) -> {
//            System.out.println("processing " + partition);
//            return RepeatStatus.FINISHED;
//        };
//    }
}
