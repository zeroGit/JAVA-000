package bytebuddytest;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

public class GeneralInterceptor {
    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments,
                            @Origin Method method) {
        // intercept any method of any signature

        return "abc";
    }
}