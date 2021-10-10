package io.github.kimmking.gateway.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;



public class NettyClientOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        DefaultFullHttpRequest request = (DefaultFullHttpRequest) msg;
        System.out.println("NettyClientOutboundHandler: "+ request);
        super.write(ctx, msg, promise);
    }
}
