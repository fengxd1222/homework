package com.example.demo.jdbc.batch;


import com.example.demo.jdbc.nativejdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class JdbcBatchUtil {

    @Autowired
    JdbcUtil jdbcUtil;

    public void insertBatch(Connection connection,List<String> names){
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into test(name) values (?)");
            for (int i = 0; i < names.size(); i++) {
                preparedStatement.setString(1,names.get(i));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void updateBatch(Connection connection,Map<Long,String> idAndName){
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("update test set name=? where id = ?");
            for (Map.Entry<Long, String> longStringEntry : idAndName.entrySet()) {
                preparedStatement.setString(1,longStringEntry.getValue());
                preparedStatement.setLong(2,longStringEntry.getKey());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * in 删除 批量删除
     * @param ids
     */
    public void deleteBatch(List<Long> ids){

    }
}
