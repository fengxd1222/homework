package com.fengxudong.api.filter;

import com.fengxudong.api.filter.chain.ResponseFilterChain;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response, ResponseFilterChain filterChain) {
        response.headers().set("kk", "java-1-nio");
        filterChain.doFilter(response);
    }

    @Override
    public int order() {
        return 0;
    }
}
