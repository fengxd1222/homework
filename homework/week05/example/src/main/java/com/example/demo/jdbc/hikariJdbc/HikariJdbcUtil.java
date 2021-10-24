package com.example.demo.jdbc.hikariJdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;


@Component
public class HikariJdbcUtil {
    @Autowired
    HikariDataSource hikariDataSource;

    public Connection getConnection(){
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
