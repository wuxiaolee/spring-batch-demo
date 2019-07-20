package com.wl.github;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/19 0019 20:33
 * @since 1.0.0
 */
//@Configuration
//@PropertySource("classpath:remote-partitioning.properties")
public class BrokerConfiguration {

//    @Value("${broker.url}")
    private String brokerUrl;

    //connectionFactory
//    @Bean
//    public ActiveMQConnectionFactory connectionFactory() {
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL(this.brokerUrl);
//        connectionFactory.setTrustAllPackages(true);
//        return connectionFactory;
//    }


}
