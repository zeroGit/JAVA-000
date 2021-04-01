package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class AopSensitiveWordsFilter {
    @Around("execution( * io.github.kimmking.gateway.filter.AopBaseFilter.channelRead(..)) " +
            "&& args(ctx,fullRequest)")
    public boolean filter(ProceedingJoinPoint jp, FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws Throwable {
        String word = fullRequest.uri().split("/")[2];
        boolean b = word.equals("xxx");
        if (!b) {
            return (boolean) jp.proceed(new Object[]{ctx, fullRequest});
        }
        ReferenceCountUtil.release(fullRequest);
        ctx.close();
        return false;
    }
}
