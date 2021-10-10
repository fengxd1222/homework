package io.github.kimmking.gateway.outbound;

import io.github.kimmking.gateway.client.NettyClient;
import io.github.kimmking.gateway.client.NettyClientInitializer;
import io.github.kimmking.gateway.client.NettyMappingHandler;
import io.github.kimmking.gateway.filter.chain.ResponseFilterChain;
import io.github.kimmking.gateway.outbound.httpclient4.NamedThreadFactory;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;

import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;


import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 自定义io写出
 */
public class NewHttpOutboundHanler extends ChannelOutboundHandlerAdapter {
    private ExecutorService proxyService;
    private final List<URL> backendUrls;

    //路由
    private HttpEndpointRouter router;

    public NewHttpOutboundHanler(List<URL> backends, HttpEndpointRouter router){
        this.router = router;
        this.backendUrls = backends;

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        NettyClientInitializer.init(backends);
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        handle(fullRequest,ctx);
        super.write(ctx, msg, promise);
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String msg = "this is gateway";
        URL backendUrl = router.route(this.backendUrls);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, backendUrl.toString(),
                Unpooled.wrappedBuffer(msg.getBytes()));
        // 构建http请求
        request.headers().set(HttpHeaderNames.HOST, backendUrl.getHost());
        request.headers().set(HttpHeaderNames.CONNECTION,
                HttpHeaderNames.CONNECTION);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                request.content().readableBytes());
        try {
            ChannelHandlerContext channelHandlerContext = NettyMappingHandler.getChannelHandlerContext(backendUrl.toString());
            Channel channel = channelHandlerContext.channel();
            channel.writeAndFlush(request);
            Channel channel1 = channelHandlerContext.fireChannelRegistered().channel();
            channelHandlerContext.writeAndFlush(request);
//            Channel channel = NettyMappingHandler.getChannel(backendUrl.toString());
//            ChannelFuture channelFuture = channel.writeAndFlush(request);
//            if(channelFuture!=null){
//                channelFuture.sync();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        proxyService.submit(()-> NettyMappingHandler.getChannel(backendUrl.getAddress().toString()).writeAndFlush(request));
    }



//    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
//        final HttpGet httpGet = new HttpGet(url);
//        //httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
//        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
//        httpGet.setHeader("mao", inbound.headers().get("mao"));
//
//        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
//            @Override
//            public void completed(final HttpResponse endpointResponse) {
//                try {
//                    handleResponse(inbound, ctx, endpointResponse);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//
//                }
//            }
//
//            @Override
//            public void failed(final Exception ex) {
//                httpGet.abort();
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void cancelled() {
//                httpGet.abort();
//            }
//        });
//    }

//    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
//        FullHttpResponse response = null;
//        try {
//            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
//
////            for (Header e : endpointResponse.getAllHeaders()) {
////                //response.headers().set(e.getName(),e.getValue());
////                System.out.println(e.getName() + " => " + e.getValue());
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//            exceptionCaught(ctx, e);
//        } finally {
//            if (fullRequest != null) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    //response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//            ctx.flush();
//            //ctx.close();
//        }
//
//    }
//
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }
}
