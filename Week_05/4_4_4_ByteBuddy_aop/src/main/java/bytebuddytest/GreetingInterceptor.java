package bytebuddytest;

public class GreetingInterceptor {
    public Object greet(Object argument) {
        return "bytebuddytest.Hello from " + argument;
    }
}