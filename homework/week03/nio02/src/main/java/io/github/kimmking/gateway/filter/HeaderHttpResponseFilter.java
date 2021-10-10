package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.filter.chain.ResponseFilterChain;
import io.netty.handler.codec.http.DefaultHttpResponse;


public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(DefaultHttpResponse response, ResponseFilterChain filterChain) {
        response.headers().set("kk", "java-1-nio");
        filterChain.doFilter(response);
    }

    @Override
    public int order() {
        return 0;
    }
}
