package com.example.demo;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceMy {
    @Autowired
    private JdbcTemplate jt;

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insertBatch(long uid1, long uid2) {
        jt.update("insert into t_order (user_id,goods_id,goods_price,goods_num,cost,create_time,stat) values(?,?,?,?,?,?,?)",
                uid1, 1, 1.0, 1, 1.0, 1, 1);
        jt.update("insert into t_order (user_id,goods_id,goods_price,goods_num,cost,create_time,stat) values(?,?,?,?,?,?,?)",
                uid2, 1, 1.0, 1, 1.0, 1, 1);
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insertBatchRollback(long uid1, long uid2) {
        jt.update("insert into t_order (user_id,goods_id,goods_price,goods_num,cost,create_time,stat) values(?,?,?,?,?,?,?)",
                uid1, 1, 1.0, 1, 1.0, 1, 1);
        // user_i 列名写错，用来测试，会导致执行异常，看会不会回滚
        jt.update("insert into t_order (user_i,goods_id,goods_price,goods_num,cost,create_time,stat) values(?,?,?,?,?,?,?)",
                uid2, 1, 1.0, 1, 1.0, 1, 1);
    }

    public void insertBatch2(long uid1, long uid2) {
        System.out.println("insertBatch2 22222222222222222222222222");
    }
}
