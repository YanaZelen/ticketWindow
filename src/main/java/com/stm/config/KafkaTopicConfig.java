package com.stm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic accountFeeTopic() {
        return TopicBuilder.name("account-fee-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic accountOperationTopic() {
        return TopicBuilder.name("account-operation-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic depositLossTopic() {
        return TopicBuilder.name("deposit-loss-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic depositOperationTopic() {
        return TopicBuilder.name("deposit-operation-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
