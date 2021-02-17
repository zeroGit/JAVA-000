package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.demo.api.ServiceDesc;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

//@Order()
@Component
public class MapResolver implements RpcfxResolver, BeanPostProcessor {

    private final ConcurrentHashMap<String, Object> handlers;

    MapResolver() {
        handlers = new ConcurrentHashMap<>();
    }

    @Override
    public Object resolve(String serviceClass) {
        return handlers.get(serviceClass);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //System.out.println("xxxxxxxxxxxxxxxxx " + beanName);

        if (bean instanceof ServiceDesc) {
            ServiceDesc sd = (ServiceDesc) bean;
            handlers.put(sd.desc(), bean);
        }
        return bean;
    }
}
