package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.ServiceDesc;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {

    private String desc = UserServiceImpl.class.getName();

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public User findById(int id) {
        System.out.println(String.format("UserServiceImpl recv + %d", id));
        return new User(id, "KK" + System.currentTimeMillis());
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
