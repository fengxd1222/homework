package homework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Thread03 {
    private static int num = 0;

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();
    public static void main(String[] args) {
        Thread03 t = new Thread03();
        Future<Integer> add = t.add(num);
        try {
            Integer integer = add.get();
            System.out.println("main线程 ："+integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Future<Integer> add(Integer input) {
        return executor.submit(() -> {
            System.out.println("add..."+ input);
            Thread.sleep(1000);
            return input +1;
        });
    }
}
