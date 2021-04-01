package io.github.kimmking.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler {
    //private final String nextServer;
    private ExecutorService nextService;

    public OkhttpOutboundHandler() {

        this.nextService = Executors.newCachedThreadPool();
    }

    public void handle(FullHttpRequest req, final ChannelHandlerContext ctx) {

        String url = req.headers().get("URL");

        nextService.submit(() -> fetchGet(req, ctx, url + req.uri()));
    }

    private void fetchGet(FullHttpRequest req, final ChannelHandlerContext ctx, final String url) {
        FullHttpResponse resp = null;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            try (Response response = client.newCall(request).execute()) {

                byte[] bts = response.body().bytes();

                resp = new DefaultFullHttpResponse(HTTP_1_1,
                        valueOf(response.code()), Unpooled.wrappedBuffer(bts));
                resp.headers().setInt("Content-Length", Integer.parseInt(response.header("Content-Length")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
        } finally {
            if (req != null) {
                if (!HttpUtil.isKeepAlive(req)) {
                    ctx.write(resp).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(resp);
                }
            }
            ctx.flush();
            ctx.close();
        }
    }
}
