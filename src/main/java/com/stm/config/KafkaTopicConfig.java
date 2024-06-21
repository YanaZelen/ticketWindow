package com.stm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic profitLossTopic() {
        return TopicBuilder.name("profitLoss-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic depositTopic() {
        return TopicBuilder.name("deposit-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic accountTopic() {
        return TopicBuilder.name("account-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
