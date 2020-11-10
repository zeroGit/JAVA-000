import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {
    public static void main(String[] args) {

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        // 使用线程池submit Callable
        thread1();
        // 使用 FutureTask 在 Thread 中执行
        thread2();
        // Thread 中更改外部变量
        thread3();

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    private static void thread1() {

        long start = System.currentTimeMillis();

        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(Homework03::sum);
        Integer integer = null;
        try {
            integer = future.get();
            es.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + integer);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private static void thread2() {

        long start = System.currentTimeMillis();

        FutureTask task = new FutureTask(Homework03::sum);

        Integer integer = null;
        {
            new Thread(task).start();

            try {
                Object o = task.get();
                integer = (Integer) o;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + integer);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private static void thread3() {

        long start = System.currentTimeMillis();

        Integer[] ia = new Integer[1];
        {
            Thread thread = new Thread(() -> {
                ia[0] = sum();
            });
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + ia[0]);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }
}
