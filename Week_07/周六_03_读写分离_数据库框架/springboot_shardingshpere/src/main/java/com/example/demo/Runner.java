package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.sql.Types;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    JdbcTemplate jt;

    @Override
    public void run(String... args) throws Exception {

        {
            Integer integer = jt.queryForObject("select user_type from user where user_id = ?",
                    new Object[]{12345},
                    new int[]{Types.BIGINT},
                    Integer.class);
            System.out.println("queryForObject : " + integer);
        }
        {
            jt.update("insert into user(user_id,user_type,reg_time) values(?,?,?)", 1990, 1, new Timestamp(System.currentTimeMillis()));
        }
        {
            Integer integer = jt.queryForObject("select user_type from user where user_id = ?",
                    new Object[]{12345},
                    new int[]{Types.BIGINT},
                    Integer.class);
            System.out.println("queryForObject : " + integer);
        }
    }
}
