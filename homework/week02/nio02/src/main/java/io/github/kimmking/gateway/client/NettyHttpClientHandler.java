package io.github.kimmking.gateway.client;


import io.github.kimmking.gateway.filter.chain.ResponseFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;



public class NettyHttpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        DefaultHttpResponse response = (DefaultHttpResponse) msg;
        new ResponseFilterChain().doFilter(response);
        System.out.println("NettyHttpClientHandler:"+response);
        ctx.writeAndFlush(msg);
    }
}
