package com.example.demo;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DistLock {
    private final StringRedisTemplate redis;
    private final String lockname;
    private static final String unlockScript = "if redis.call(\"get\", KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 2 end";

    public DistLock(StringRedisTemplate template, String lockname) {
        this.redis = template;
        this.lockname = "l_" + lockname;
    }

    public boolean lock(int timeout) {

        int loopCnt = 0;
        if (timeout <= 0) {
            loopCnt = 1;
        } else {
            loopCnt = timeout * 10;
        }

        for (int i = loopCnt; i >= 0; i--) {

            if (timeout <= 0) {
                i++;
            }

            Boolean lockRet = redis.opsForValue().setIfAbsent(lockname, lockname, Duration.ofSeconds(100));
            if (lockRet != null && lockRet.equals(Boolean.TRUE)) {
                return true;
            }

            if (i < 0) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }

        return false;
    }

    public boolean unLock() {
        DefaultRedisScript<Long> unlockRedisScript = new DefaultRedisScript<>(unlockScript, Long.class);
        Long ret = redis.execute(unlockRedisScript, Collections.singletonList(lockname), lockname);

        if (ret == null) {
            return false;
        }

        if (ret == 2) {
            return false;
        }

        return true;
    }
}
