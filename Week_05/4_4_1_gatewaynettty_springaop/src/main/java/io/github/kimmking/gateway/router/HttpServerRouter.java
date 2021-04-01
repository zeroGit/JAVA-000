package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class HttpServerRouter extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            // /b1/abc -> https://www.baidu.com/s?wd=abc
            // /b2/abc -> https://cn.bing.com/search?q=abc

            if (msg instanceof FullHttpRequest) {
                String serv = "";
                FullHttpRequest request = (FullHttpRequest) msg;
                // "/a/b" -> "", "a", "b"
                String s1 = request.uri().split("/")[1];
                switch (s1) {
                    case "b1":
                        serv = "https://www.baidu.com/s?wd=" + request.uri().split("/")[2];
                        break;

                    case "b2":
                        serv = "https://www.sogou.com/web?query=" + request.uri().split("/")[2];
                        break;
                }
                if (!serv.isEmpty()) {
                    super.channelRead(ctx, serv);
                } else {
                    ctx.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
