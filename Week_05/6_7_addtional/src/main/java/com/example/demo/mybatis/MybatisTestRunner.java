package com.example.demo.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.util.List;

@Component
public class MybatisTestRunner implements CommandLineRunner {

    @Autowired
    UserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userMapper.allUsers();
        users.forEach(u -> {
            System.out.println("user : " + u.getUserId());
        });
    }
}
