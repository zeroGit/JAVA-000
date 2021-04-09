package com.example.demo.idgenerator;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {

    // 每个服务的下一个可用的id
    // long 最大正数
    // 9223372036854775807
    // 前三位 922，作为服务标记，共 0 - 922 个标记
    // 后面的位数，作为id了，从0开始累加
    // 顺序的id只能内部服务相互使用
    private final static long[] serverIds;

    static {
        serverIds = new long[923];
        for (int i = 0; i < 923; i++) {
            serverIds[i] = i * 10000000000000000L;
        }
    }

    public synchronized static long get(int servid) {
        long id = serverIds[servid];
        serverIds[servid] = id + 1;
        return id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println("get " + IdGenerator.get(3));
        }
    }
}
