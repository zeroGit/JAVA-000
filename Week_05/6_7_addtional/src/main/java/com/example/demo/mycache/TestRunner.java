package com.example.demo.mycache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    UseCache useCache;

    @Override
    public void run(String... args) throws Exception {
        int cnt = 0;

        while (true) {

            Thread.sleep(1000);

            ++cnt;
            System.out.println("" + cnt + "," + useCache.getInt());
        }
    }
}
