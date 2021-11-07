package com.example.demo.dynamic.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component("dynamicDatasourceConfig")
@ConditionalOnProperty(prefix = "dynamicdb",name = {"names"})
@Data
public class DynamicDatasourceConfig{

    @Value("${dynamicdb.names}")
    private List<String> names;

    DataSource dataSource;

    @Autowired
    Environment environment;

    Map<String, DataSource> dataSourceMap = new HashMap<>();

    
    @PostConstruct
    public void setEnvironment() {
        dataSourceMap.put("master",initDefaultDatasource(environment));
        initDynamicDatasource(environment);
        DynamicDatasourceHolder.datasourceName.addAll(dataSourceMap.keySet());
    }

    private DataSource initDefaultDatasource(Environment environment) {
        Iterable sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        BindResult bindResult = binder.bind("spring.datasource.druid", Properties.class);
        Properties properties= (Properties) bindResult.get();
        DataSource dataSource = buildDataSource(properties);
        this.dataSource = dataSource;
        return dataSource;
    }


    private void initDynamicDatasource(Environment environment) {
        Iterable sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        for (String name : names) {
            BindResult bindResult = binder.bind("dynamicdb.datasource."+name, Properties.class);
            Properties properties= (Properties) bindResult.get();
            DataSource dataSource = buildDataSource(properties);
            dataSourceMap.put(name,dataSource);
        }
    }
    public DataSource buildDataSource(Properties properties) {
        try {
            String type = properties.getProperty("type");
            if(type==null){
                type = "com.alibaba.druid.pool.DruidDataSource";
            }
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(type);
            String driverClassName = properties.get("driver-class-name").toString();
            String url = properties.get("url").toString();
            String username = properties.get("username").toString();
            String password = properties.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
