package io.github.kimmking.gateway.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public class NettyMappingHandler{

    public static ChannelHandlerContext getChannelHandlerContext(String server) throws Exception {
        Map<Integer, NettyClient> channelMapping = NettyClientInitializer.getChannelMapping();
        if(channelMapping==null || channelMapping.isEmpty()){
            throw new Exception("NettyClientInitializer needs to be initialized");
        }
        NettyClient nettyClient = channelMapping.get(server.hashCode());
        if(nettyClient==null){
            throw new Exception("The nettyClient corresponding to the server["+server+"] is invalid");
        }
        ChannelHandlerContext ctx = nettyClient.getHolder().getCtx();
        return ctx;
    }

}
