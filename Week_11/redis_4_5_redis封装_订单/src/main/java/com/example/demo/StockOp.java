package com.example.demo;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class StockOp {

    private static final String reduceScript = "local r = redis.call(\"decrby\", KEYS[1], ARGV[1])\n" +
            "if r >= 0 then\n" +
            "   return r\n" +
            "else\n" +
            "   redis.call(\"set\", KEYS[1], 0)\n" +
            "   return r\n" +
            "end\n";

    private final StringRedisTemplate redis;
    private final String stockName;
    private final RedisScript<Long> reduceRedisScript = new DefaultRedisScript<Long>(reduceScript, Long.class);

    public StockOp(StringRedisTemplate redis, String stockName) {
        this.redis = redis;
        this.stockName = stockName;
    }

    // 返回增加后的总库存
    public long add(int inc) {
        Optional<Long> remain = Optional.ofNullable(redis.opsForValue().increment(stockName, inc));
        return remain.orElse(-1L);
    }

    // 返回成功减少的库存
    public long reduce(int r) {
        Optional<Long> remain = Optional.ofNullable(redis.execute(reduceRedisScript, Collections.singletonList(stockName), Integer.toString(r)));
        if (remain.isPresent()) {
            if (remain.get() >= 0) {
                return r;
            } else {
                return r + remain.get();
            }
        }
        return -1;
    }
}
