package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisTest implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    @Qualifier("OrderListenerContainer")
    private RedisMessageListenerContainer orderListenerContainer;

    @Override
    public void run(String... args) throws Exception {
        // 分布式锁
        DistLock testA = new DistLock(redisTemplate, "testA");
        testA.lock(1);
        testA.unLock();

        // 模拟减库存
        StockOp apple = new StockOp(redisTemplate, "Apple");
        System.out.println("add 10 -> " + apple.add(10));
        System.out.println("add 10 -> " + apple.add(10));
        System.out.println("red 10 -> " + apple.reduce(10));
        System.out.println("red 3 -> " + apple.reduce(3));
        System.out.println("red 10 -> " + apple.reduce(10));
        System.out.println("red 10 -> " + apple.reduce(10));

        // 模拟订单处理

        OrderOp appleOrder = new OrderOp(redisTemplate, "AppleOrder");
        appleOrder.submit("order 1");

        System.out.println("ccccccccccccc " + orderListenerContainer);
    }
}
