package aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 通知，包含 在某个方法上使用哪些方法去处理
// befores
// afters
public class Advice {
    private List<Method> befores = new ArrayList<>();
    private List<Method> afters = new ArrayList<>();

    // befores afters 方法需要相应类对象来调用
    private Map<Class<?>, Object> adviceObject = new ConcurrentHashMap<>();
    private Map<Method, Object> methodObject = new ConcurrentHashMap<>();

    // before 以 . 分隔，最后一部分是方法名
    // 方法名中，() 括起来的中间，包含参数类型名称
    // before 示例 : AdviceMethod.beforeFuncABC(int,int,String)
    public Advice before(String before) throws Exception {
        addAdvice(before, befores);
        return this;
    }

    private void addAdvice(String advice, List<Method> advicesLi) throws Exception {

        try {

            MethodDesc methodDesc = MethodDesc.analMethodDesc(advice);

            advicesLi.add(methodDesc.getM());

            Object o = adviceObject.get(methodDesc.getC());
            if (o == null) {
                o = methodDesc.getC().newInstance();
                adviceObject.put(methodDesc.getC(), o);
            }
            methodObject.putIfAbsent(methodDesc.getM(), o);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    //public Advice before(String[] before) {
    //    return this;
    //}

    public Advice after(String after) throws Exception {
        addAdvice(after, afters);
        return this;
    }

    //public Advice around(String around) {
    //    return this;
    //}

    // 作用于哪个method上
    public Aspect onMethod(String method) throws Exception {
        Aspect aspect = new Aspect(this, method);
        aspect.init();
        return aspect;
    }

    void invokeBefore() {
        befores.forEach(m -> {
            Object o = methodObject.get(m);
            if (o != null) {
                try {
                    m.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("invokeBefore err : " + m.getName());
                }
            }
        });
    }

    void invokeAfter() {
        afters.forEach(m -> {
            Object o = methodObject.get(m);
            if (o != null) {
                try {
                    m.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("invokeBefore err : " + m.getName());
                }
            }
        });
    }
}

