package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    WriteService wService;

    @Autowired
    ReadService rService;

    @Override
    public void run(String... args) throws Exception {

        wService.NewUser(12345);

        int userType = rService.getUserType(12345);
        System.out.println("getUserType "+12345+", "+userType);
        userType = rService.getUserType(22345);
        System.out.println("getUserType "+22345+", "+userType);
    }
}
