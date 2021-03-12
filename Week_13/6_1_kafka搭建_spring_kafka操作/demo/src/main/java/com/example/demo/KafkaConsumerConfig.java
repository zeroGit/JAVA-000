package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory(KafkaProperties props) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory(props));

        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(KafkaProperties props) {

        return new DefaultKafkaConsumerFactory<String, String>(props.buildConsumerProperties());
    }
}
