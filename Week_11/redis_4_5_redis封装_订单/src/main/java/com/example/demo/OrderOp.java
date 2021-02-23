package com.example.demo;

import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Collection;
import java.util.Collections;

public class OrderOp {

    private final StringRedisTemplate redis;
    private final String orderName;

    public OrderOp(StringRedisTemplate redis, String orderName) {
        this.redis = redis;
        this.orderName = orderName;
    }

    public void submit(String order) {
        redis.convertAndSend(orderName, order);
    }

    public static class OrderListner implements MessageListener {
        @Override
        public void onMessage(Message message, byte[] pattern) {
            System.out.println("Get order : " + message);
        }
    }
}
