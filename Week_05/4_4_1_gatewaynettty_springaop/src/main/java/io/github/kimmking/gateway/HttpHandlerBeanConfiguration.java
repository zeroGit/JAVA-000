package io.github.kimmking.gateway;

import io.github.kimmking.gateway.filter.AopBaseFilter;
import io.github.kimmking.gateway.filter.HttpServerFilter;
import io.github.kimmking.gateway.router.HttpServerRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("io.github.kimmking.gateway")
@EnableAspectJAutoProxy
public class HttpHandlerBeanConfiguration {
    @Bean("filter")
    HttpServerFilter httpServerFilter() {
        return new HttpServerFilter();
    }

    @Bean("aopFilter")
    AopBaseFilter aopBaseFilter() {
        return new AopBaseFilter();
    }

    @Bean("router")
    HttpServerRouter httpServerRouter() {
        return new HttpServerRouter();
    }
}
