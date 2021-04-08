package com.example.demo.mycache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

@Aspect
@Component
public class MyChacheAop {

    private static class CacheValue {
        Object value;
        long expiredTime;
        int cacheTime;
    }

    // 可以注入一个专门的管理器
    private Map<String, CacheValue> cacheValues = new HashMap<>();

    @Around("@annotation(com.example.demo.mycache.MyCache)")
    int aroundInt(ProceedingJoinPoint jp) {

        long curtime = System.currentTimeMillis();

        try {

            String methodName = jp.getSignature().getName();

            CacheValue value = cacheValues.computeIfAbsent(methodName, name -> {
                try {
                    Object[] args = jp.getArgs();
                    Class<?>[] cs = new Class<?>[args.length];

                    for (int i = 0; i < args.length; i++) {
                        cs[i] = args[i].getClass();
                    }
                    int t = jp.getTarget().getClass().getMethod(name, cs).getAnnotation(MyCache.class).value();
                    CacheValue cacheValue = new CacheValue();
                    cacheValue.cacheTime = t;

                    return cacheValue;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });

            if (value == null) return -1;

            if (value.value != null && curtime <= value.expiredTime) {
                return (int) value.value;
            }

            Object ret = jp.proceed(jp.getArgs());
            value.expiredTime = curtime + value.cacheTime * 1000;
            value.value = ret;
            return (int) value.value;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return -1;
    }
}
