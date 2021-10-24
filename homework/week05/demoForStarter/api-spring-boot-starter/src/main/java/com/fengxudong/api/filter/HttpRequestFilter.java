package com.fengxudong.api.filter;

import com.fengxudong.api.filter.chain.RequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;


public interface HttpRequestFilter {

    
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, RequestFilterChain filterChain);

    /**
     * filter执行顺序
     * @return
     */
    int order();

    /**
     * 是否还需要执行 此过滤器的filter方法以及之后所有的过滤器 ，此方法有优先于filter()
     * @param fullRequest
     * @param ctx
     * @return
     */
    boolean shouldFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
