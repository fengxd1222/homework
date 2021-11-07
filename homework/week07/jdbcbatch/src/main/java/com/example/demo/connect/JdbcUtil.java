package com.example.demo.connect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.*;

@Configuration
public class JdbcUtil {

    @Value("${drivername}")
    private String drivername;
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;


    @PostConstruct
    public void connect(){
        try {
            Class.forName(drivername);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void close(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
