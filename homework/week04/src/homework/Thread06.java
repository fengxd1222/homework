package homework;

import java.util.concurrent.Semaphore;

public class Thread06 {
    private static int num = 0;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            try {
                num++;
                Thread.sleep(3000);
                System.out.println("新线程");
            }catch (Exception e){
                e.printStackTrace();
            }
            semaphore.release();
        }).start();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);
        System.out.println("main线程");

    }
}
