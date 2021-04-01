package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AopInvocationHandler implements InvocationHandler {
    private Object ori;
    private Aspect aspect;

    public AopInvocationHandler(Object ori, Aspect aspect) {
        this.ori = ori;
        this.aspect = aspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // jdk Proxy 是一个实现了传入的接口的方法的类

        aspect.invokeBefore(method);
        Object ret = method.invoke(ori, args);
        aspect.invokeAfter(method);

        return ret;
    }
}

