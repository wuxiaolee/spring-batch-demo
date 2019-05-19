package com.wl.config.jms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//import org.springframework.amqp.rabbit.connection.ConnectionFactory;

//import org.springframework.integration.dsl.jms.Jms;

/**
 * <远程分区通讯>
 *
 * @author wulei
 * @date 2019/5/19 0019 21:00
 * @since 1.0.0
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.rabbit")
public class MasterJmsConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;
    private int connRecvThreads;
    private int channelCacheSize;

    /**
     * connect rabbitMQ
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(connRecvThreads);
        executor.initialize();
        connectionFactory.setExecutor(executor);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setChannelCacheSize(channelCacheSize);
        return connectionFactory;
    }

    /**
     * Configure outbound flow (requests going to workers)
     */
    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("partition.requests", false);
    }

    @Bean
    @ServiceActivator(inputChannel = "requests")
    public AmqpOutboundEndpoint amqpOutboundEndpoint(AmqpTemplate template) {
        AmqpOutboundEndpoint endpoint = new AmqpOutboundEndpoint(template);
        endpoint.setExpectReply(true);
        endpoint.setOutputChannel(replies());
        endpoint.setRoutingKey("partition.requests");
        return endpoint;
    }

    @Bean
    public MessagingTemplate messageTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate(requests());
        messagingTemplate.setReceiveTimeout(60000000L);
        return messagingTemplate;
    }

    /**
     * Configure inbound flow (replies coming from workers)
     */
    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

//    @Bean
//    public QueueChannel replies() {
//        return new QueueChannel();
//    }

    @Bean
    public AmqpInboundChannelAdapter inbound(SimpleMessageListenerContainer listenerContainer) {
        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(replies());
        adapter.afterPropertiesSet();
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("partition.requests");
        container.setAutoStartup(false);

        return container;
    }

    @Bean
    public PollableChannel outboundStaging() {
        return new NullChannel();
    }

}
