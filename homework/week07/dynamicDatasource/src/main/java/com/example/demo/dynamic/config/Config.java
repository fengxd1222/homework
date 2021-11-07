package com.example.demo.dynamic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnBean(value = DynamicDatasourceConfig.class)
@DependsOn("dynamicDatasourceConfig")
public class Config {
    @Autowired
    DynamicDatasourceConfig dynamicDatasourceConfig;
    @Bean
    public DataSource dataSource(){
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        dynamicDatasource.setDefaultTargetDataSource(dynamicDatasourceConfig.dataSource);
        //设置默认数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.putAll(dynamicDatasourceConfig.dataSourceMap);
        dynamicDatasource.setTargetDataSources(dsMap);
        return dynamicDatasource;
    }
}
