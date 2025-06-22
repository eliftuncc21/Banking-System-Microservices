package org.eliftunc.userservice.kafka;

import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.kafka.KafkaProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    private final KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();

    @Bean
    public ProducerFactory<String, UserCreatedEvent> kafkaProducer() {
        return kafkaProducerConfig.kafkaProducer();
    }

    @Bean
    public KafkaTemplate<String, UserCreatedEvent> kafkaTemplate() {
        return kafkaProducerConfig.kafkaTemplate();
    }

}
