package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.valueOf;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NettyHttpClientProxyHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        NettyHttpClient nettyHttpClient = NettyHttpClient.newCli((String) msg, "");
        NettyHttpClient.RespInfo res = nettyHttpClient.getResBody();
        //System.out.println("QQQQQQQQQQQQQQQQQ " + res.getBody());

        byte[] bts = res.getBody().getBytes();

        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1,
                valueOf(res.getCode()), Unpooled.wrappedBuffer(bts));
        resp.headers().set(res.getHeaders());

        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }
}