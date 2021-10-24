package com.fengxudong.api.configuration;

import com.fengxudong.api.router.configuration.APIRouterConfiguration;
import lombok.Data;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({APIRouterConfiguration.class, NettyServerConfiguration.class})
@ConditionalOnProperty(
        prefix = "api.server",
        name = "enable",
        havingValue = "true"
)
@Data
public class APIGlobalConfiguration {

    private final APIRouterConfiguration routerConfiguration;

    private final NettyServerConfiguration nettyServerConfiguration;

    @Generated
    public APIGlobalConfiguration(APIRouterConfiguration routerConfiguration,
                                  NettyServerConfiguration nettyServerConfiguration){
        this.routerConfiguration = routerConfiguration;
        this.nettyServerConfiguration = nettyServerConfiguration;
    }
}
