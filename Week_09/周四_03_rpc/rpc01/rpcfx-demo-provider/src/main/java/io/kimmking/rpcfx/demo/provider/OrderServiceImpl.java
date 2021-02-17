package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.ServiceDesc;

public class OrderServiceImpl implements OrderService {

    private String desc = OrderServiceImpl.class.getName();

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public Order findOrderById(int id) {
        System.out.println(String.format("OrderServiceImpl recv + %d", id));
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
