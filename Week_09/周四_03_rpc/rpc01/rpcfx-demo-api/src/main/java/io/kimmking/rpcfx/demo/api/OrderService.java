package io.kimmking.rpcfx.demo.api;

public interface OrderService extends ServiceDesc {

    Order findOrderById(int id);

}
