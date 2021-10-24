package com.fengxudong.api.inbound;
import com.fengxudong.api.filter.chain.RequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.URL;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<URL> proxyServer;
    ApplicationContext applicationContext;

    public HttpInboundHandler(List<URL> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            //request过滤器 可以优化为 和request绑定，超出一定时间回收;或者使用netty的channelHandler当做过滤器
            applicationContext.getBean(RequestFilterChain.class).doFilter(fullRequest,ctx);
            ctx.writeAndFlush(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
