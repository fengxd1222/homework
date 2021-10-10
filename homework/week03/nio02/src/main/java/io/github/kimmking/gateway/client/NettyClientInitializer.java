package io.github.kimmking.gateway.client;

import io.netty.channel.Channel;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NettyClientInitializer {

    private static volatile NettyClientInitializer initializer = null;

    private static final Map<Integer, NettyClient> channelMapping = new HashMap<>();

    private NettyClientInitializer(List<URL> proxyServers) {
        initNettyClient(proxyServers);
    }

    public static NettyClientInitializer init(List<URL> proxyServers) {
        if (initializer == null) {
            synchronized (NettyClientInitializer.class) {
                if (initializer == null) {
                    initializer = new NettyClientInitializer(proxyServers);
                }
            }
        }
        return initializer;
    }


    private void initNettyClient(List<URL> proxyServers) {
        if (!proxyServers.isEmpty()) {
            for (URL proxyServer : proxyServers) {
                NettyClient nettyClient = new NettyClient(proxyServer);
                nettyClient.run();
                channelMapping.put(proxyServer.toString().hashCode(), nettyClient);
            }
        }
    }


    protected static Map<Integer, NettyClient> getChannelMapping() {
        return channelMapping;
    }
}
