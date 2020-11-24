package com.example.demo;

import a.b.c.beans.School;
import a.b.c.beans.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        Student student = (Student) run.getBean("student");
        System.out.println("+++++++++++++ " + student);
    }

}

