package com.fengxudong.api.router.configuration;


import com.fengxudong.api.router.HttpEndpointRouter;
import com.fengxudong.api.router.holder.RouterHolder;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

@Data
@ConfigurationProperties(prefix = "api.router")
@ConditionalOnBean(value = HttpEndpointRouter.class)
public class APIRouterConfiguration implements ApplicationContextAware {

    private String name;


    private ApplicationContext applicationContext;
    @PostConstruct
    public void initRouter() throws Exception {
        new RouterHolder((HttpEndpointRouter) applicationContext.getBeansOfType(HttpEndpointRouter.class).get(name));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
