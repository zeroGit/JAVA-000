import aop.Aop;
import com.sun.org.apache.xpath.internal.operations.Or;

public class Main {
    public static void main(String[] args) {

        try {

            Original original = new Original();

            // jdk 代理必须有一个接口
            //FuncA funcA = (FuncA) Aop.newAdvice()

            // 对于 bytebuddy，不需要，使用原类型即可
            Original funcA = (Original) Aop.newAdvice()
                    // 创建通知，说明要在某个方法上添加哪些方法处理
                    .before("AdviceMethod.beforeFuncA()")
                    .before("AdviceMethod.beforeFuncB()")
                    .after("AdviceMethod.afterFuncA()")
                    .after("AdviceMethod.afterFuncA()")
                    // 返回一个切面，指明了要在这个方法上 添加上述的通知
                    // jdk 需要写上接口的方法，因为需要代理接口方法
                    //.onMethod("FuncA.func2String(java.lang.String , java.lang.String )")
                    // bytebuddy 需要写明实际类的方法
                    .onMethod("Original.func2String(java.lang.String , java.lang.String )")
                    // 返回一个代理，指明上述切面作用于这个对象上
                    // spring 的切面也只能作用于 Bean上
                    .bytebuddy(original);

            funcA.func();
            System.out.println("-----------");
            funcA.func2String("abc", "123");
            System.out.println("-----------");
            funcA.funcString("11111111111");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
