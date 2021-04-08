package com.example.demo.mycache;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UseCache {
    @MyCache(10)
    public int getInt() {
        int i = new Random(System.currentTimeMillis()).nextInt();
        System.out.println("UseCache get " + i);
        return i;
    }
}
