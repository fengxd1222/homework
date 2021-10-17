package homework;


public class Thread04 {
    private static int num = 0;

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                num++;
                Thread.sleep(3000);
                System.out.println("新线程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);
        System.out.println("main 线程");
    }
}
