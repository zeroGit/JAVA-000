package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class SensitiveWordsFilter implements HttpRequestFilter {

    public SensitiveWordsFilter() {
    }

    @Override
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String word = fullRequest.uri().split("/")[2];
        if (word.equals("敏感")) {
            return false;
        }

        return true;
    }
}
