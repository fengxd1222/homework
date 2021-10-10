package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.filter.chain.RequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, RequestFilterChain filterChain) {
        System.out.println("进入 HeaderHttpRequestFilter.filter ");
        fullRequest.headers().set("mao", "soul");
        filterChain.doFilter(fullRequest,ctx);
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public boolean shouldFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        return true;
    }
}
