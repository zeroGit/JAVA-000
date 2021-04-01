import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class TimerAgent {
    public static void premain(String arguments,
                               Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("Main"))
                .transform(
                        (builder, type, classLoader, module) -> {
                            return builder.method(ElementMatchers.namedOneOf("testTimed"))
                                    .intercept(MethodDelegation.to(TimingInterceptor.class));
                        })
                .installOn(instrumentation);
    }
}