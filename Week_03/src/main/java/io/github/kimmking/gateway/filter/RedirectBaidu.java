package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class RedirectBaidu implements HttpRequestFilter {

    public RedirectBaidu() {
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.uri();
        fullRequest.headers().add("URL", "http://www.baidu.com");
        if (uri.equals("/spring")) {
            // 搜索 spring
            fullRequest.setUri("/s?wd=spring");
        }
    }
}
