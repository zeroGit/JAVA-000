package com.example.demo.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
@MapperScan("com.example.demo.mybatis")
public class MybatisConfiguration {
}
