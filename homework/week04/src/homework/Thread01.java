package homework;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CountDownLatch;

/**
 * 第一种方式 通过CountDownLatch实现
 */
public class Thread01 {

    private static int num = 0;
    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(1);

        new Thread(()->{
            try {
                num++;
                Thread.sleep(5000);
                System.out.println("新线程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            downLatch.countDown();
        }).start();

        try {
            downLatch.await();
            System.out.println(num);
            System.out.println("主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
