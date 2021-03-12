package com.example.demo;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KafkaTest implements ApplicationRunner {

    @Autowired
    KafkaTemplate<String, String> kafka;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                System.out.println("xxxxxxxxxxx " + Arrays.toString(args.getSourceArgs()));
                System.out.println("xxxxxxxxxxx " + args.getOptionNames());
                System.out.println("xxxxxxxxxxx " + args.getNonOptionArgs());


                int cnt = 0;

                //noinspection InfiniteLoopStatement
                while (true) {
                    cnt++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    kafka.send("newTopic", "abcdefg1" + String.format("-%d", cnt));
                }
            }
        };

        new Thread(runnable).start();
    }
}
