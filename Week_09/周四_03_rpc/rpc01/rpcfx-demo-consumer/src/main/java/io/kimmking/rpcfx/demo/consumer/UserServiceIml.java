package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceIml implements UserService {
    final private User user = new User();

    @Override
    public User findById(int id) {
        return user;
    }

    @Override
    public String desc() {
        return "UserService";
    }

    @Override
    public void setDesc(String desc) {

    }
}
