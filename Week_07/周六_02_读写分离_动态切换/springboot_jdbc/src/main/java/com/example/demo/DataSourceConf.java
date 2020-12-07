package com.example.demo;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConf {
    // w 写
    @Primary
    @Bean("dspw")
    @ConfigurationProperties(prefix = "spring.dsw")
    public DataSourceProperties dspw() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("dsw")
    public DataSource dsw() {
        return dspw().initializeDataSourceBuilder().build();
    }

    @Bean("jtw")
    public JdbcTemplate jtw() {
        return new JdbcTemplate(dsw());
    }


    // r 读
    @Bean("dspr")
    @ConfigurationProperties(prefix = "spring.dsr")
    public DataSourceProperties dspr() {
        return new DataSourceProperties();
    }

    @Bean("dsr")
    public DataSource dsr() {
        return dspr().initializeDataSourceBuilder().build();
    }

    @Bean("jtr")
    public JdbcTemplate jtr() {
        return new JdbcTemplate(dsr());
    }
}
