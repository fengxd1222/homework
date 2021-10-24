package com.fengxudong.api.inbound;

import com.fengxudong.api.outbound.HttpOutboundHandler;
import com.fengxudong.api.router.holder.RouterHolder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.URL;
import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private final List<URL> proxyServer;


    public HttpInboundInitializer(List<URL> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpOutboundHandler(this.proxyServer,RouterHolder.getROUTER()));
        p.addLast(new HttpInboundHandler(this.proxyServer));

    }
}
