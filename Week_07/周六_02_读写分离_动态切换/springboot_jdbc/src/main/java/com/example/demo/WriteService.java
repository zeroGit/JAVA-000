package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class WriteService {

    @Autowired
    @Qualifier("jtw")
    JdbcTemplate jt;

    public void NewUser(long uid) {
        jt.update("insert into user(user_id,user_type,reg_time) values(?,?,?)", uid, 1, new Timestamp(System.currentTimeMillis()));
    }
}
