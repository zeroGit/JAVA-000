package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RpcfxCliTestRunner implements CommandLineRunner {
    //@Autowired
    //RpcfxCliAspect aspect;

    @Autowired
    UserServiceIml userService;
    @Autowired
    OrderServiceIml orderService;

    @Override
    public void run(String... args) throws Exception {

        User user = userService.findById(1);
        if (user != null) {
            System.out.println("find user id=1 from server: " + user.getName());
        } else {
            System.out.println("find user null");
        }

        Order order = orderService.findOrderById(1992129);
        if (order != null) {
            System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
        } else {
            System.out.println("find order null");
        }

        //
        //UserService userService2 = Rpcfx.createFromRegistry(UserService.class, "localhost:2181", new RpcfxClientApplication.TagRouter(), new RpcfxClientApplication.RandomLoadBalancer(), new RpcfxClientApplication.CuicuiFilter());
    }
}
