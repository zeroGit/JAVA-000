package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    KafkaListenerContainerFactory<?> factory;

    @KafkaListener(topics = {"newTopic"}, groupId = "cc1")
    public void listen(String data) {
        System.out.println("xxxxxxxxxxxxxxxxxxx " + data);
    }
}
