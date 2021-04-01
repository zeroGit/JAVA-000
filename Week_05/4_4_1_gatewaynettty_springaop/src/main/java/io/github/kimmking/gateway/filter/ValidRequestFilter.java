package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class ValidRequestFilter implements HttpRequestFilter {
    @Override
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String[] parts = fullRequest.uri().split("/");
        // "/a/b" -> "", "a", "b"
        return parts.length == 3;
    }
}
