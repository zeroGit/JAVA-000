package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceIml implements OrderService {

    final private Order order = new Order(0, "", 1.0f);

    @Override
    public Order findOrderById(int id) {
        return order;
    }

    @Override
    public String desc() {
        return "OrderService";
    }

    @Override
    public void setDesc(String desc) {

    }
}
