package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 课程演示代码
 */
public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);
        ServerSocket serverSocket = new ServerSocket(8801);

        while (true){
            try {
                Socket accept = serverSocket.accept();
                executorService.execute(()->service(accept));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket accept) {
        try {
            PrintWriter printWriter = new PrintWriter(accept.getOutputStream(),true);
            printWriter.println("HTTP/1.1 200 ok");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "Hello World";
            printWriter.println("Content-Length:"+body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
            accept.close();
        }catch (Exception e){

        }

    }
}
