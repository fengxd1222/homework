package com.fengxudong.api.filter;

import com.fengxudong.api.filter.chain.RequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

@Component
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
