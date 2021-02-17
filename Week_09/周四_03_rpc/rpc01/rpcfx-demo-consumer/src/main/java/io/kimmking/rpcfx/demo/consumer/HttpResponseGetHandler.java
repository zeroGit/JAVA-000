package io.kimmking.rpcfx.demo.consumer;

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

            cli.setResBody(res.content().toString(CharsetUtil.UTF_8));

            super.channelRead(ctx, msg);
        }
    }
}
