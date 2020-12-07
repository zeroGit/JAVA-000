package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Optional;

@Service
public class ReadService {
    @Autowired
    @Qualifier("jtr")
    JdbcTemplate jdbcTemplate;

    public int getUserType(long uid) {

        try {
            Integer integer = jdbcTemplate.queryForObject("select user_type from user where user_id = ?",
                    new Object[]{uid},
                    new int[]{Types.BIGINT},
                    Integer.class);
            return Optional.ofNullable(integer).orElse(-1);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }
}
