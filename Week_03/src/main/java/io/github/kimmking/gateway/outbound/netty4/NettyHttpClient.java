package io.github.kimmking.gateway.outbound.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URLEncoder;

import static io.netty.handler.codec.http.HttpResponseStatus.valueOf;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NettyHttpClient {

    public static class RespInfo {
        private int code;

        public HttpHeaders getHeaders() {
            return headers;
        }

        public void setHeaders(HttpHeaders headers) {
            this.headers = headers;
        }

        private HttpHeaders headers;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        private String body;
    }

    private static final Bootstrap bs = newBootStrap();

    private RespInfo respInfo = new RespInfo();
    private ChannelPromise resFuture;
    private int code;

    private NettyHttpClient() {
    }

    private static Bootstrap newBootStrap() {
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        Bootstrap boot = new Bootstrap();
        boot.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)      // 非主要功能
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        //p.addLast(new HttpResponseDecoder());
                        //p.addLast(new HttpRequestEncoder());
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(512 * 1024));
                        //p.addLast(new HttpResponseGetHandler(null));
                        // gzip
                        //p.addLast(new HttpContentDecompressor());
                        //p.addLast(new EchoClientHandle());
                    }
                });

        return boot;
    }

    public static NettyHttpClient newCli(String url, String body) throws Exception {
        URI uri = new URI(url);
        String path = uri.getPath();
        if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
            //path += "?" + URLEncoder.encode(uri.getQuery(), "UTF-8");
            path += "?" + uri.getRawQuery();
        }
        if (path == null || path.isEmpty()) {
            path = "/";
        }

        return newCli(uri.getHost(), uri.getPort() <= 0 ? 80 : uri.getPort(), path, body);
    }

    private static NettyHttpClient newCli(String host, int port, String path, String body) throws Exception {

        NettyHttpClient cli = new NettyHttpClient();
        ChannelFuture connect = bs.connect(host, port);
        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ByteBuf bb = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
                    DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path, bb);
                    request.headers().set(HttpHeaderNames.HOST, host);
                    //request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
                    //request.headers().set(HttpHeaderNames.CONTENT_LENGTH, bb.writerIndex() + 1);
                    request.headers().set(HttpHeaderNames.ACCEPT, "*/*");
                    request.headers().set(HttpHeaderNames.USER_AGENT, "curl/7.47.1");
                    connect.channel().pipeline().addLast(new HttpResponseGetHandler(cli));

                    future.channel().writeAndFlush(request);
                }
            }
        });
        connect.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("close");
                cli.resFuture.setSuccess();
            }
        });


        cli.resFuture = new DefaultChannelPromise(connect.channel());

        return cli;
    }

    public RespInfo getResBody() throws InterruptedException {
        resFuture.sync();
        return respInfo;
    }

    public void setResBody(int code, String body, HttpHeaders headers) {
        respInfo.code = code;
        respInfo.body = body;
        respInfo.headers = headers;
        resFuture.setSuccess();
    }

    public static void main(String[] args) {
    }
}
