package aop;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyWrapper {

    private final Aspect aspect;
    private final Object original;
    private Class<?> bytebuddyType;

    public ByteBuddyWrapper(Aspect aspect, Object original) {
        this.aspect = aspect;
        this.original = original;
    }

    public void init() {
        bytebuddyType = new ByteBuddy()
                .subclass(original.getClass())
                .method(ElementMatchers.namedOneOf(aspect.getProxyMethod().getName()))
                .intercept(MethodDelegation.to(new ByteBuddyInterception(original, aspect)))
                .make()
                .load(this.getClass().getClassLoader())
                .getLoaded();

    }

    public Object getInterceptor() throws Exception {
        return bytebuddyType.newInstance();
    }
}
