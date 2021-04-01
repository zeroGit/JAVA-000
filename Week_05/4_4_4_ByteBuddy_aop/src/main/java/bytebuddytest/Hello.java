package bytebuddytest;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Hello {

    public static void main(String[] args) {

        try {
            {
                Class<?> dynamicType = new ByteBuddy()
                        .subclass(Object.class)
                        .name("a.b.c.BB1")
                        .method(ElementMatchers.named("toString"))

                        .intercept(FixedValue.value("bytebuddytest.Hello World!"))

                        .make()

                        .load(Hello.class.getClassLoader())
                        .getLoaded();

                System.out.println(dynamicType.newInstance().toString());
            }

            {
                Class<? extends java.util.function.Function> dynamicType = new ByteBuddy()
                        .subclass(java.util.function.Function.class)
                        .method(ElementMatchers.named("apply"))
                        //.intercept(MethodDelegation.to(new bytebuddytest.GreetingInterceptor()))
                        .intercept(MethodDelegation.to(new GeneralInterceptor()))
                        .make()
                        .load(Hello.class.getClassLoader())
                        .getLoaded();

                System.out.println(dynamicType.newInstance().apply("Byte Buddy"));
            }

            FunctionA functionA = new FunctionA();
            System.out.println(functionA.apply("123456"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
