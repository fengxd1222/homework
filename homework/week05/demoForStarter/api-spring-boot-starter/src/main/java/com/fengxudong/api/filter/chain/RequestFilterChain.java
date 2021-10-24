package com.fengxudong.api.filter.chain;


import com.fengxudong.api.filter.FilterInitializer;
import com.fengxudong.api.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 过滤器执行链
 */
@Component
@Scope("prototype")
public class RequestFilterChain {
    private List<HttpRequestFilter> httpRequestFilters;

    private int index = 0;

    @Autowired
    public RequestFilterChain(FilterInitializer filterInitializer){
        httpRequestFilters = filterInitializer.getHttpRequestFilters();
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
