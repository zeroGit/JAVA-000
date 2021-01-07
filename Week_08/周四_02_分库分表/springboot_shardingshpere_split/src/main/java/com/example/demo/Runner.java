package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
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
            try {
                Integer integer = jt.queryForObject("select stat from t_order where user_id = ?",
                        new Object[]{12345},
                        new int[]{Types.BIGINT},
                        Integer.class);
                System.out.println("queryForObject : " + integer);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        {
            try {
                Integer integer = jt.queryForObject("select stat from t_order where order_id = ?",
                        new Object[]{111167890},
                        new int[]{Types.BIGINT},
                        Integer.class);
                System.out.println("queryForObject : " + integer);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
