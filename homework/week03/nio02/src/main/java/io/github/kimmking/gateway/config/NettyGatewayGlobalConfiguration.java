package io.github.kimmking.gateway.config;

import io.github.kimmking.gateway.filter.FilterInitializer;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.github.kimmking.gateway.token.AESTokenHandler;
import io.github.kimmking.gateway.token.TokenHandler;
import io.github.kimmking.gateway.token.TokenHandlerExecutor;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * 网关全局配置类
 */
public class NettyGatewayGlobalConfiguration {


    //路由配置
    private HttpEndpointRouter router;
    //token校验类
    private TokenHandler tokenHandler;

    private int port;

    private List<URL> proxyServers;

    //默认配置下，采取随机路由、token为aes加解密
    public NettyGatewayGlobalConfiguration(int port, List<URL> proxyServers){
        this.port = port;
        this.proxyServers = proxyServers;
        this.router = new RandomHttpEndpointRouter();
        tokenHandler(AESTokenHandler.getInstance());
        initFilter();
    }

    private void initFilter() {
        FilterInitializer instance = FilterInitializer.getInstance();
    }

    public NettyGatewayGlobalConfiguration router(HttpEndpointRouter router){
        this.router = router;
        return this;
    }

    public NettyGatewayGlobalConfiguration tokenHandler(TokenHandler tokenHandler){
        this.tokenHandler = tokenHandler;
        new TokenHandlerExecutor(this.tokenHandler);
        return this;
    }

    public HttpEndpointRouter getRouter() {
        return router;
    }

    public TokenHandler getTokenHandler() {
        return tokenHandler;
    }

    public int getPort() {
        return port;
    }

    public List<URL> getProxyServers() {
        return proxyServers;
    }
}
