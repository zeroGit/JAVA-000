
作业：

默认gateway是访问 www.baidu.com

#### 周四 - 1.整合你上次作业的 httpclient/okhttp
实现了 `OkhttpOutboundHandler` 类的方法，
替换掉了 `HttpInboundHandler` 构造方法中的原 handler
```
    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        filter = new RedirectBaidu();
        handler = new OkhttpOutboundHandler();
    }
```

#### 周六 - 1.实现过滤器
实现了一个 RedirectBaidu 过滤器，当url 路径是 `/spring` 的时候，百度直接搜索 spring
在 `HttpInboundHandler` 中直接添加了这个 filter
```
    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        filter = new RedirectBaidu();
        handler = new OkhttpOutboundHandler();
    }
```

