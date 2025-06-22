package org.eliftunc.notificationservice.kafka;

import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.kafka.KafkaConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class ConsumerConfig {
    private final KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

    @Bean
    public ConsumerFactory<String, UserCreatedEvent> consumerFactory(){
        return kafkaConsumerConfig.consumerFactory("notification-service-group", UserCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> kafkaListenerContainerFactory(){
        return kafkaConsumerConfig.kafkaListenerContainerFactory(consumerFactory());
    }
}
