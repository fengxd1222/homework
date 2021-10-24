package com.fengxudong.api.configuration;

import com.fengxudong.api.inbound.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Data
@ConfigurationProperties(prefix = "api.server")
public class NettyServerConfiguration {

    private Logger log = LoggerFactory.getLogger(NettyServerConfiguration.class);
    /**
     * Listening host
     */
    private String host;
    /**
     * Listening port
     */
    private Integer port;

    /**
     * Forwarding proxy address
     */
    private List<String> urls;

    private int bossGroupThreads;

    private int workerGroupThreads;

    /**
     * The option for workerGroup
     */
    private Map<String,Object> childOptions = new HashMap<>();
    /**
     * The option for bossGroup
     */
    private Map<String,Object> options = new HashMap<>();



    @PostConstruct
    public void initNettyServer(){
        log.info("NettyServer starts to initialize...");
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreads);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreads);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 128);
            for (Map.Entry<String, Object> optionEntry : options.entrySet()) {
                b.option(ChannelOption.valueOf(optionEntry.getKey()),optionEntry.getValue());
                log.info("Option : "+optionEntry.getKey()+" | "+optionEntry.getValue());
            }

            for (Map.Entry<String, Object> childOptionEntry : childOptions.entrySet()) {
                b.childOption(ChannelOption.valueOf(childOptionEntry.getKey()),childOptionEntry.getValue());
                log.info("childOption : "+childOptionEntry.getKey()+" | "+childOptionEntry.getValue());
            }
            b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new HttpInboundInitializer(toURL()));
            Channel channel = b.bind(host, port).sync().channel();
            log.info("开启netty http服务器，监听地址和端口为 "+host+":" + port + '/');
            channel.closeFuture().sync();
        }catch (Exception e){
            log.error("NettyServerError" , e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private List<URL> toURL(){
        return urls.stream().map(url -> {
            URL server;
            try {
                server = new URL(url);
                return server;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
