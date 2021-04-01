package aop;

import java.lang.reflect.Proxy;

class AopProxy {
    private Aspect aspect;
    private Object original;

    // jdk Proxy 是一个实现了传入的接口的方法的类
    private Object proxy;

    AopProxy(Aspect aspect, Object original) {
        this.aspect = aspect;
        this.original = original;
    }

    void init() throws Exception {
        // 使用的是 jdk 的proxy
        AopInvocationHandler handelr = new AopInvocationHandler(original, aspect);
        // jdk Proxy 是一个实现了传入的接口的方法的类
        proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{aspect.getProxyClass()}, handelr);
    }

    Object getProxy() {
        return proxy;
    }
}

