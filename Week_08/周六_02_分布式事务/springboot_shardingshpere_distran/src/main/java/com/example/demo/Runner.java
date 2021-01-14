package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    ServiceMy s;

    @Override
    public void run(String... args) throws Exception {
        s.insertBatch(5001, 5002);

        s.insertBatchRollback(6001, 6002);
    }
}
