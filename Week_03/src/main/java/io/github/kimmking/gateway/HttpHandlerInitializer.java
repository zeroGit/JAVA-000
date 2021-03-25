package io.github.kimmking.gateway;

import io.github.kimmking.gateway.filter.HttpServerFilter;
import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.github.kimmking.gateway.outbound.netty4.NettyHttpClientProxyHandler;
import io.github.kimmking.gateway.router.HttpServerRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpHandlerInitializer extends ChannelInitializer<SocketChannel> {

    public HttpHandlerInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpServerFilter());
        //p.addLast(new HttpInboundHandler());
        p.addLast(new HttpServerRouter());
        p.addLast(new NettyHttpClientProxyHandler());
    }
}
