package com.example.demo.jdbc.hikariJdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariConfiguration {
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    @Bean
    public HikariDataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        return new HikariDataSource(config);
    }
}
