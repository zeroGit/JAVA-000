package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HttpServerFilter extends ChannelInboundHandlerAdapter {

    private final List<HttpRequestFilter> filters = new ArrayList<>();

    public HttpServerFilter() {
        filters.add(new ValidRequestFilter());
        filters.add(new SensitiveWordsFilter());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            if (msg instanceof FullHttpRequest) {
                for (HttpRequestFilter filter : filters) {
                    if (!filter.filter((FullHttpRequest) msg, ctx)) {
                        // 需要回复一条消息
                        ReferenceCountUtil.release(msg);
                        ctx.close();
                        return;
                    }
                }
            }

            super.channelRead(ctx, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //finally {
        //    ReferenceCountUtil.release(msg);
        //}
    }
}
