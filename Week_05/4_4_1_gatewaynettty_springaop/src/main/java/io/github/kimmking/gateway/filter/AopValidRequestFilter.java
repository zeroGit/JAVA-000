package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AopValidRequestFilter {
    @Around("execution( * io.github.kimmking.gateway.filter.AopBaseFilter.channelRead(..)) " +
            "&& args(ctx,fullRequest)")
    public boolean filter(ProceedingJoinPoint jp, FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws Throwable {
        String[] parts = fullRequest.uri().split("/");
        // "/a/b" -> "", "a", "b"
        boolean b = parts.length == 3;
        if (b) {
            return (boolean) jp.proceed(new Object[]{ctx, fullRequest});
        }
        ReferenceCountUtil.release(fullRequest);
        ctx.close();
        return false;
    }
}

