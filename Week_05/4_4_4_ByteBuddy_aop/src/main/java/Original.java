public class Original implements FuncA {
    @Override
    public void funcString(String s) {
        System.out.println("funcString : " + s);
    }

    @Override
    public void func() {
        System.out.println("this is Original's func");
    }

    @Override
    public void func2String(String s1, String s2) {
        System.out.println("funcString 2 : " + s1 + ", " + s2);
    }
}
