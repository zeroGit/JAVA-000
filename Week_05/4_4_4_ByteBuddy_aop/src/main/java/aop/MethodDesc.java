package aop;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodDesc {

    private Class<?> c;
    private Method m;
    private String classS;
    private String methodS;

    public MethodDesc(Class<?> c, Method m, String classS, String methodS) {
        this.c = c;
        this.m = m;
        this.classS = classS;
        this.methodS = methodS;
    }

    public static MethodDesc analMethodDesc(String advice) throws Exception {

        int i = advice.indexOf('(');
        if (i == -1) {
            throw new ClassNotFoundException("before format error");
        }

        String classname, methodname;

        classname = advice.substring(0, i);
        i = classname.lastIndexOf('.');
        if (i == -1) {
            throw new ClassNotFoundException("before format error");
        }
        classname = classname.substring(0, i);
        methodname = advice.substring(i + 1, advice.length());

        i = methodname.indexOf('(');
        if (i == -1) {
            throw new ClassNotFoundException("before format error");
        }
        int i2 = methodname.indexOf(')');
        if (i2 == -1 || i2 != methodname.length() - 1) {
            throw new ClassNotFoundException("before format error");
        }
        String[] split = methodname.substring(i + 1, i2).split(",");
        ArrayList<Class<?>> args = new ArrayList<>();
        for (String s : split) {
            String trim = s.trim();
            if (trim.isEmpty()) {
                continue;
            }
            args.add(Class.forName(trim));
        }

        Class<?> c = Class.forName(classname);
        methodname = methodname.substring(0, i);
        Method m;
        if (args.isEmpty()) {
            m = c.getMethod(methodname);
        } else {
            m = c.getMethod(methodname, args.toArray(new Class<?>[]{}));
        }

        return new MethodDesc(c, m, classname, methodname);
    }

    public Class<?> getC() {
        return c;
    }

    public Method getM() {
        return m;
    }

    public String getClassS() {
        return classS;
    }

    public String getMethodS() {
        return methodS;
    }
}
