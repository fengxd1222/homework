package io.github.kimmking.gateway.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;



import java.net.URL;


public class NettyClient {
    private Channel channel;
//    private ChannelFuture channelFuture;
    private final URL uri;

    private NettyContextHolder holder;

    public NettyClient(URL proxyServer) {
        this.uri = proxyServer;
    }

    public void run() {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        NettyContextHolder nettyContextHolder = new NettyContextHolder();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                .option(EpollChannelOption.SO_REUSEPORT, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpResponseDecoder());
                        pipeline.addLast(new HttpRequestEncoder());
                        pipeline.addLast(nettyContextHolder);
                        pipeline.addLast(new NettyClientOutboundHandler());
                        pipeline.addLast(new NettyHttpClientHandler());
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect(uri.getHost(),uri.getPort()).sync();
            channelFuture.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture arg0) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("连接服务器成功");
                    } else {
                        System.out.println("连接服务器失败");
                        channelFuture.cause().printStackTrace();
//                        workGroup.shutdownGracefully();
                    }
                }
            });
            this.channel = channelFuture.channel();
            this.holder = nettyContextHolder;
//            URI uri = new URI(host+":"+port);
//            DefaultFullHttpRequest request = new DefaultFullHttpRequest(
//                    HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
//                    Unpooled.wrappedBuffer(msg.getBytes()));
//            // 构建http请求
//            request.headers().set(HttpHeaderNames.HOST, host);
//            request.headers().set(HttpHeaderNames.CONNECTION,
//                    HttpHeaderNames.CONNECTION);
//            request.headers().set(HttpHeaderNames.CONTENT_LENGTH,
//                    request.content().readableBytes());
//            channelFuture.channel().write(request);
//            channelFuture.channel().flush();
//            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public NettyContextHolder getHolder() {
        return holder;
    }

    //    public DefaultFullHttpRequest buildRequest(String msg){
//        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
//                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
//                Unpooled.wrappedBuffer(msg.getBytes()));
//        // 构建http请求
//        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
//        request.headers().set(HttpHeaderNames.CONNECTION,
//                HttpHeaderNames.CONNECTION);
//        request.headers().set(HttpHeaderNames.CONTENT_LENGTH,
//                request.content().readableBytes());
//        return request;
//    }

//    public static void main(String[] args) throws URISyntaxException {
//        NettyClient nettyClient = new NettyClient(new URI("localhost:8888"));
//        nettyClient.run();
//        Channel channel = nettyClient.getChannel();
//        channel.writeAndFlush(nettyClient.buildRequest("hello , this is nettyClient"));
//    }
}
