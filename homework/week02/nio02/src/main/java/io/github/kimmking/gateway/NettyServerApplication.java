package io.github.kimmking.gateway;


import io.github.kimmking.gateway.config.NettyGatewayGlobalConfiguration;
import io.github.kimmking.gateway.inbound.HttpInboundServer;
import io.github.kimmking.gateway.router.RoundRobinEndpointRouter;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NettyServerApplication {
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";
    
    public static void main(String[] args) throws URISyntaxException, MalformedURLException {

        String proxyPort = System.getProperty("proxyPort","8888");

        // 这是之前的单个后端url的例子
//        String proxyServer = System.getProperty("proxyServer","http://localhost:8088");
//          //  http://localhost:8888/api/hello  ==> gateway API
//          //  http://localhost:8088/api/hello  ==> backend service
        // java -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar  #作为后端服务


        // 这是多个后端url走随机路由的例子
//        String proxyServers = System.getProperty("proxyServers","http://localhost:8801,http://localhost:8802");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");

        URL server1 = new URL("http://127.0.0.1:8801");
        URL server2 = new URL("http://127.0.0.1:8802");
        List<URL> proxyServers = new ArrayList<>();
        proxyServers.add(server1);
        proxyServers.add(server2);


        //配置类
        NettyGatewayGlobalConfiguration configuration = new NettyGatewayGlobalConfiguration(port, proxyServers);
        //自定义实现一个轮询路由，并配置到配置类中
        configuration.router(new RoundRobinEndpointRouter());
        HttpInboundServer server = new HttpInboundServer(configuration);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
