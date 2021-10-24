package com.fengxudong.api.filter;

import com.fengxudong.api.filter.chain.ResponseFilterChain;
import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    void filter(FullHttpResponse response, ResponseFilterChain filterChain);
    /**
     * filter执行顺序
     * @return
     */
    int order();
}
