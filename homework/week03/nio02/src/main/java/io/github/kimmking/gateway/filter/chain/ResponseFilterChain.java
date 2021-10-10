package io.github.kimmking.gateway.filter.chain;

import io.github.kimmking.gateway.filter.FilterInitializer;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import java.util.List;

public class ResponseFilterChain {
    private List<HttpResponseFilter> httpResponseFilters;

    private int index = 0;

    public ResponseFilterChain(){
        httpResponseFilters = FilterInitializer.getInstance().getHttpResponseFilters();
    }

    public void doFilter(DefaultHttpResponse response){
        if(index==httpResponseFilters.size()){
            return;
        }
        HttpResponseFilter httpResponseFilter = httpResponseFilters.get(index);
        index++;
        httpResponseFilter.filter(response,this);
    }
}
