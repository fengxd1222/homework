package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.filter.chain.ResponseFilterChain;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    void filter(DefaultHttpResponse response, ResponseFilterChain filterChain);
    /**
     * filter执行顺序
     * @return
     */
    int order();
}
