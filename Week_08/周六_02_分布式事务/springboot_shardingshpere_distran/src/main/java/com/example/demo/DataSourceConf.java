package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConf {

    @Bean("jt")
    public JdbcTemplate jt(DataSource ds) {
        System.out.println("DataSource ----- " + ds.getClass());
        return new JdbcTemplate(ds);
    }
}
