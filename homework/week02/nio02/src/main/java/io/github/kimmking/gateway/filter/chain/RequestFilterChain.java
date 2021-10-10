package io.github.kimmking.gateway.filter.chain;

import io.github.kimmking.gateway.filter.FilterInitializer;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * 过滤器执行链
 */
public class RequestFilterChain {
    private List<HttpRequestFilter> httpRequestFilters;

    private int index = 0;

    public RequestFilterChain(){
        httpRequestFilters = FilterInitializer.getInstance().getHttpRequestFilters();
    }

    public void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
        if(index==httpRequestFilters.size()){
            return;
        }
        HttpRequestFilter httpRequestFilter = httpRequestFilters.get(index);
        if(!httpRequestFilter.shouldFilter(fullRequest,ctx)){
            return;
        }
        index++;
        httpRequestFilter.filter(fullRequest,ctx,this);
    }
}
