package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) {
        SpringApplication.run(RpcfxClientApplication.class, args);
    }

    public static class TagRouter implements Router {
        @Override
        public List<String> route(List<String> urls) {
            return urls;
        }
    }

    public static class RandomLoadBalancer implements LoadBalancer {
        @Override
        public String select(List<String> urls) {
            return urls.get(0);
        }
    }

    @Slf4j
    public static class CuicuiFilter implements Filter {
        @Override
        public boolean filter(RpcfxRequest request) {
            log.info("filter {} -> {}", this.getClass().getName(), request.toString());
            return true;
        }
    }
}



