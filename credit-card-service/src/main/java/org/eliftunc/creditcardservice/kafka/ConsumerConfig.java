package org.eliftunc.creditcardservice.kafka;

import org.eliftunc.events.PaymentCreatedEvent;
import org.eliftunc.kafka.KafkaConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class ConsumerConfig {
    private final KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

    @Bean
    public ConsumerFactory<String, PaymentCreatedEvent> consumerFactory(){
        return kafkaConsumerConfig.consumerFactory("credit-card-service-group", PaymentCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> kafkaListenerContainerFactory(){
        return kafkaConsumerConfig.kafkaListenerContainerFactory(consumerFactory());
    }
}
