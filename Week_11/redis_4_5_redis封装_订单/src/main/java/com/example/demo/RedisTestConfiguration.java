package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Collections;

@Configuration
public class RedisTestConfiguration {
    @Bean("OrderListenerContainer")
    RedisMessageListenerContainer redisMessageListenerContainer(StringRedisTemplate redis) {

        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(redis.getConnectionFactory());
        listenerContainer.setMessageListeners(Collections.singletonMap(new OrderOp.OrderListner(), Collections.singleton(new PatternTopic("Apple*"))));
        return listenerContainer;
    }
}
