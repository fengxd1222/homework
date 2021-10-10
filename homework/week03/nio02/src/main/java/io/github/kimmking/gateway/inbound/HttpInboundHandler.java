package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.chain.RequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URI;
import java.net.URL;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<URL> proxyServer;

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
            new RequestFilterChain().doFilter(fullRequest, ctx);
            ctx.writeAndFlush(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用crx.write  不用手动释放
//        finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

}
