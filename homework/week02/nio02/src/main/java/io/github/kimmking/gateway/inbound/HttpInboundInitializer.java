package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.config.NettyGatewayGlobalConfiguration;
import io.github.kimmking.gateway.outbound.NewHttpOutboundHanler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private final List<URL> proxyServer;

    private final HttpEndpointRouter router;


    public HttpInboundInitializer(NettyGatewayGlobalConfiguration configuration) {
        this.proxyServer = configuration.getProxyServers();
        this.router = configuration.getRouter();
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new NewHttpOutboundHanler(this.proxyServer, router));
        p.addLast(new HttpInboundHandler(this.proxyServer));

    }
}
