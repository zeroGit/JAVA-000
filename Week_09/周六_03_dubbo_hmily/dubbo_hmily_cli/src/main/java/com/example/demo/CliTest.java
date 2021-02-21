package com.example.demo;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CliTest implements CommandLineRunner {

    @Reference
    private TransferService transferService;

    @Override
    public void run(String... args) throws Exception {
        String ret = transferService.transfer(1, 2);
        System.out.println("rrrrrrrrrrr ret : " + ret);
    }
}
