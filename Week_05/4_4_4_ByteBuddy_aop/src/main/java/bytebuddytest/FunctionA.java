package bytebuddytest;

import java.util.function.Function;

public class FunctionA implements Function<String, String> {
    @Override
    public String apply(String s) {
        return s;
    }
}
