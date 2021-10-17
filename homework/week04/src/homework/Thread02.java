package homework;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用locksupport.park unpark
 * 场景有限 如果新线程里的任务执行的过快，会导致unpark先于park执行
 */
public class Thread02 {
    private static int num = 0;
    public static void main(String[] args) {
        Thread02 obj = new Thread02();
        Thread mainThread = Thread.currentThread();
        new Thread(()->{
            try {
                num++;
                Thread.sleep(5000);
                System.out.println("新线程");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LockSupport.unpark(mainThread);
        }).start();
        LockSupport.park(obj);
        System.out.println(num);
        System.out.println("主线程");
    }
}
