package io.github.kimmking.gateway;

import io.github.kimmking.gateway.filter.HttpServerFilter;
import io.github.kimmking.gateway.outbound.netty4.NettyHttpClientProxyHandler;
import io.github.kimmking.gateway.router.HttpServerRouter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HttpHandlerBeanAopInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) {

        try {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
            ctx.register(HttpHandlerBeanConfiguration.class);
            ctx.refresh();

            //Object filter = ctx.getBean("filter");
            Object filter = ctx.getBean("aopFilter");
            Object router = ctx.getBean("router");

            ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
            p.addLast(new HttpServerCodec());
            //p.addLast(new HttpServerExpectContinueHandler());
            p.addLast(new HttpObjectAggregator(1024 * 1024));

            p.addLast((ChannelHandler) filter);
            //p.addLast(new HttpInboundHandler());
            p.addLast((ChannelHandler) router);

            p.addLast(new NettyHttpClientProxyHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
