package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.filter.chain.RequestFilterChain;
import io.github.kimmking.gateway.token.TokenHandlerExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

/**
 * 校验token合法性的过滤器，过滤器执行链首位
 * 需要考虑跨域情况下，首次访问的options请求
 */
public class TokenHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, RequestFilterChain filterChain) {
        System.out.println("进入 TokenHttpRequestFilter.filter ");
        String token = fullRequest.headers().get("token");
        if(token==null || token.equals("")){
            return;
        }
        //这里做一个简单的校验逻辑，如果解密之后获取的时间戳，与当前时间戳相差五秒以上，视为失败
        String decrypt = TokenHandlerExecutor.decrypt(token);
        try {
            Long timeFromRequest = Long.valueOf(decrypt);
            long currentTimeMillis = System.currentTimeMillis();
            if(timeFromRequest>currentTimeMillis || currentTimeMillis-timeFromRequest>5000){
                return;
            }
            filterChain.doFilter(fullRequest,ctx);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int order() {
        return 0;
    }

    /**
     * 判断是否是跨域请求的options方法，如果是，直接放行，不需要执行后续过滤器
     * @param fullRequest
     * @param ctx
     * @return true 需要执行下面的过滤器  false 不需要执行，直接跳出
     */
    @Override
    public boolean shouldFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        return fullRequest.method().compareTo(HttpMethod.OPTIONS)!=0;
    }

}
