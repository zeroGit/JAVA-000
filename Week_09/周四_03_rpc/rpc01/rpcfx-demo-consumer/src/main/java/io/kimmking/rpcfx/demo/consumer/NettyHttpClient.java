package io.kimmking.rpcfx.demo.consumer;

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
import org.apache.commons.lang.StringUtils;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class NettyHttpClient {

    private static final Bootstrap bs = newBootStrap();

    private String resBody;
    private ChannelPromise resFuture;

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
        if (uri.getQuery() != null && uri.getQuery().isEmpty()) {
            path += "?" + URLEncoder.encode(uri.getQuery(), "UTF-8");
        }
        if (path == null || path.isEmpty()) {
            path = "/";
        }

        return newCli(uri.getHost(), uri.getPort(), path, body);
    }

    private static NettyHttpClient newCli(String host, int port, String path, String body) throws Exception {

        NettyHttpClient cli = new NettyHttpClient();
        ChannelFuture connect = bs.connect(host, port);
        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ByteBuf bb = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
                    DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, path, bb);
                    request.headers().set(HttpHeaderNames.HOST, host);
                    request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
                    request.headers().set(HttpHeaderNames.CONTENT_LENGTH, bb.writerIndex() + 1);
                    future.channel().writeAndFlush(request);

                    connect.channel().pipeline().addLast(new HttpResponseGetHandler(cli));
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

    public String getResBody() throws InterruptedException {
        resFuture.sync();
        return resBody;
    }

    public void setResBody(String body) {
        resBody = body;
        resFuture.setSuccess();
    }

    public static void main(String[] args) {
        try {
            {
                NettyHttpClient nettyHttpClient = NettyHttpClient.newCli("http://47.93.207.16:9098/abc?a=我", "abcdefg");
                String res = nettyHttpClient.getResBody();
                System.out.println("QQQQQQQQQQQQQQQQQ " + res);
            }

            System.out.println("222222222222222222222222222222222222222");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
