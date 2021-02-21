package com.example.demo;

import org.apache.dubbo.config.annotation.Service;

@Service
public class TransferServiceImpl implements TransferService {
    @Override
    public String transfer(long userA, long userB) {
        System.out.printf("transfer - userA : %d -> usreB : %d%n", userA, userB);
        return "OK";
    }
}
