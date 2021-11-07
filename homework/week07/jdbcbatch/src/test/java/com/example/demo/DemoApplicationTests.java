package com.example.demo;

import com.example.demo.connect.JdbcUtil;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {


    int cors = Runtime.getRuntime().availableProcessors();
    private ExecutorService proxyService = new ThreadPoolExecutor(cors, cors * 2,
            1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Autowired
    JdbcUtil jdbcUtil;

    @Test
    void contextLoads() {
    }


    /**
     * 一次一次提交
     * 10w数据 194秒
     */
    @Test
    public void testA(){
        long l = System.currentTimeMillis();
        Connection connection = jdbcUtil.getConnection();
        try {
            for (int i = 0; i < 100000; i++) {
                Statement statement = connection.createStatement();
                String sql = "insert into mall_commodity(brand_id,title,description) values ("+(i+1)+",'title"+i+"','description"+i+"')";
                statement.executeUpdate(sql);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l)/1000);
    }

    /**
     * 预编译执行
     * 批量执行 自动提交事务 5秒 （可能是硬件配置太好了？）
     */
    @Test
    public void testB(){
        long l = System.currentTimeMillis();
        Connection connection = jdbcUtil.getConnection();
        String sql = "insert into mall_commodity(brand_id,title,description) values (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 100_0000; i++) {
                preparedStatement.setLong(1,(i+1));
                preparedStatement.setString(2,"title"+i);
                preparedStatement.setString(3,"description"+i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l)/1000);
    }

    /**
     * 多线程执行
     *  还是5秒
     */
    @Test
    public void testC(){


        Connection connection = jdbcUtil.getConnection();
        String sql = "insert into mall_commodity(brand_id,title,description) values (?,?,?)";
        List<Map<String,Object>> datas = new ArrayList<>(1000000);
        for (int i = 0; i < 100_0000; i++) {
            Map<String,Object> data = new HashMap<>();
            data.put("brand_id",i+1);
            data.put("title","title"+1);
            data.put("description","description"+1);
            datas.add(data);
        }
        long l = System.currentTimeMillis();
        try {
            insertByThread(datas,connection,sql);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l)/1000);
    }

    private void insertByThread(List<Map<String,Object>> datas,Connection connection,String sql) throws InterruptedException {
        List<List<Map<String, Object>>> partition = Lists.partition(datas, 100000);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());
        partition.forEach(part->{
            proxyService.execute(new Runnable() {
                @Override
                public void run() {
                    insertBatch(part,connection,sql);
                    countDownLatch.countDown();
                }
            });
        });
        countDownLatch.await();
    }

    private void insertBatch(List<Map<String,Object>> datas,Connection connection,String sql){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (Map<String, Object> data : datas) {
                preparedStatement.setLong(1, Long.parseLong(data.get("brand_id").toString()));
                preparedStatement.setString(2, (String) data.get("title"));
                preparedStatement.setString(3, (String) data.get("description"));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
