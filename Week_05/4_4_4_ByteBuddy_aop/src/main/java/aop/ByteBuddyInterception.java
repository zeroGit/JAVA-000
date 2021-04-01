package aop;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

public class ByteBuddyInterception {

    private final Object original;
    private final Aspect aspect;

    ByteBuddyInterception(Object original, Aspect aspect) {
        this.original = original;
        this.aspect = aspect;
    }

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments,
                            @Origin Method method) throws Exception {

        aspect.invokeBefore(method);
        Object ret = method.invoke(original, allArguments);
        aspect.invokeAfter(method);
        return ret;
    }
}
