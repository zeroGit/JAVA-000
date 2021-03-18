package io.kimmking.kmq.beans;

import io.kimmking.kmq.core.KmqBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KmqBeansConfig {
    @Bean
    public KmqBroker kmqBroker() {
        String topic = "kk.test";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);
        return broker;
    }
}
