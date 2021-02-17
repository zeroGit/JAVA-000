package io.kimmking.rpcfx.demo.api;

public interface UserService extends ServiceDesc {

    User findById(int id);

}
