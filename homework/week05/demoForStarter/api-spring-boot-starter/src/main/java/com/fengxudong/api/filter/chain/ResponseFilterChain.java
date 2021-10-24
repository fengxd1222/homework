package com.fengxudong.api.filter.chain;


import com.fengxudong.api.filter.FilterInitializer;
import com.fengxudong.api.filter.HttpResponseFilter;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Scope("prototype")
public class ResponseFilterChain {
    private List<HttpResponseFilter> httpResponseFilters;

    private int index = 0;

    @Autowired
    public ResponseFilterChain(FilterInitializer filterInitializer){
        httpResponseFilters = filterInitializer.getHttpResponseFilters();
    }

    public void doFilter(FullHttpResponse response){
        if(index==httpResponseFilters.size()){
            return;
        }
        HttpResponseFilter httpResponseFilter = httpResponseFilters.get(index);
        index++;
        httpResponseFilter.filter(response,this);
    }
}
