package com.example.demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@MapperScan(basePackages = {"com.example.demo.logic.mapper"})
@SpringBootApplication(exclude= {DruidDataSourceAutoConfigure.class})
public class DemoApplication {

    public static void main(String[] args) throws IOException {
//        Properties properties = new Properties();
//        InputStream stream = DemoApplication.class.getClassLoader().getResourceAsStream("application-sharding.yaml");
//        properties.load(stream);
        SpringApplication app = new SpringApplication(DemoApplication.class);
//        app.setDefaultProperties(properties);
//        app.setEnvironment();
        app.run(args);
    }

}
