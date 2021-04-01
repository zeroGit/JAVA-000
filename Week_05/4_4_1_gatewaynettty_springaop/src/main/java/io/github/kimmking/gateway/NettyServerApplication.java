package io.github.kimmking.gateway;


public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    // 服务的作用，是代理，代理访问，类似于 nginx
    // 测试的是搜索代理，根据请求的url，确定去哪个搜索引擎去搜索
    // /b1/abc -> https://www.baidu.com/s?wd=abc
    // /b2/abc -> https://cn.bing.com/search?q=abc
    public static void main(String[] args) {

        String proxyServer = System.getProperty("proxyServer", "https://www.baidu.com;https://cn.bing.com/search?q=spring");
        String proxyPort = System.getProperty("proxyPort", "8888");

        //  http://localhost:8888/api/hello  ==> gateway API
        //  http://localhost:8088/api/hello  ==> backend service

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpServer server = new HttpServer(port, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + proxyServer);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
