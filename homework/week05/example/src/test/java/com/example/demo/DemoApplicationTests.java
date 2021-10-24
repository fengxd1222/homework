package com.example.demo;

import com.example.demo.jdbc.batch.JdbcBatchUtil;
import com.example.demo.jdbc.hikariJdbc.HikariJdbcUtil;
import com.example.demo.jdbc.nativejdbc.JdbcUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    JdbcUtil jdbcUtil;
    @Autowired
    JdbcBatchUtil jdbcBatchUtil;
    @Autowired
    HikariJdbcUtil hikariJdbcUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert() throws SQLException {
        Connection connection = jdbcUtil.getConnection();
        Statement statement = connection.createStatement();

        int i = jdbcUtil.insertOrUpdate(statement, "insert into test(name) values ('native')");
    }

    @Test
    public void testSelect() throws SQLException {
        Connection connection = jdbcUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = jdbcUtil.select(statement, "select * from test where id=1");
        while (resultSet.next()){
            System.out.println(resultSet.getObject("name"));
        }
    }

    @Test
    public void testInsertBatch()throws Exception{
        Connection connection = jdbcUtil.getConnection();
        List<String> names = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            names.add("batch"+i);
        }
        jdbcBatchUtil.insertBatch(connection,names);
    }

    @Test
    public void testUpdateBatch()throws Exception{
        Connection connection = jdbcUtil.getConnection();
        Map<Long,String> idAndName = new HashMap<>(50);

        for (int i = 0; i < 50; i++) {
            idAndName.put((long) i+1,"afterUpdate"+i);
        }
        jdbcBatchUtil.updateBatch(connection,idAndName);
    }

    @Test
    public void testInsertHikari()throws Exception{
        Connection connection = hikariJdbcUtil.getConnection();
        List<String> names = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            names.add("Hikaribatch"+i);
        }
        jdbcBatchUtil.insertBatch(connection,names);
    }

    @Test
    public void testUpdateHikari()throws Exception{
        Connection connection = hikariJdbcUtil.getConnection();
        Map<Long,String> idAndName = new HashMap<>(50);

        for (int i = 0; i < 50; i++) {
            idAndName.put((long) i+1,"afterHikariUpdate"+i);
        }
        jdbcBatchUtil.updateBatch(connection,idAndName);
    }
}
