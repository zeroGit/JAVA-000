package io.github.kimmking.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpResponseGetHandler extends ChannelInboundHandlerAdapter {

    private final NettyHttpClient cli;

    public HttpResponseGetHandler(NettyHttpClient cli) {
        this.cli = cli;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse res = (FullHttpResponse) msg;

            cli.setResBody(res.status().code(), res.content().toString(CharsetUtil.UTF_8), res.headers());

            super.channelRead(ctx, msg);
        }
    }
}
