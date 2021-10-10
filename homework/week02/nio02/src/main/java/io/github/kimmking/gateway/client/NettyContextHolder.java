package io.github.kimmking.gateway.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyContextHolder extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx(){
        return ctx;
    }
}
