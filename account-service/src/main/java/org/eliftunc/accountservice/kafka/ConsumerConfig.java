package org.eliftunc.accountservice.kafka;

import org.eliftunc.events.TransactionCreatedEvent;
import org.eliftunc.kafka.KafkaConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class ConsumerConfig {
    private final KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

    @Bean
    public ConsumerFactory<String, TransactionCreatedEvent>consumerFactory() {
        return kafkaConsumerConfig.consumerFactory("account-service-group", TransactionCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionCreatedEvent> kafkaListenerContainerFactory() {
        return kafkaConsumerConfig.kafkaListenerContainerFactory(consumerFactory());
    }
}
