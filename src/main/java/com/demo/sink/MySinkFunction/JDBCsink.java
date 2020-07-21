package com.demo.sink.MySinkFunction;


import com.demo.entity.Comsu;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCsink extends RichSinkFunction<Comsu> {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private PreparedStatement preparedStatement;
    // JDBC连接信息
    String USERNAME = "root";
    String PASSWORD = "171774";
    String DBURL = "jdbc:mysql://175.24.130.58:3306/sinkMysql";
    /**
     * open方法是初始化方法，会在invoke方法之前执行，执行一次。
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        // 加载JDBC驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接
        connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        String sql = "insert into sinktest (id,name) values (?,?)";
        preparedStatement = connection.prepareStatement(sql);
        super.open(parameters);
    }


    @Override
    public void invoke(Comsu value, Context context) throws Exception {
        try {
            preparedStatement.setString(1,value.getId());
            preparedStatement.setString(2,value.getName());
            System.out.println("执行SQL-->"+preparedStatement.toString());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close()是tear down的方法，在销毁时执行，关闭连接。
     */
    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
        super.close();
    }
}

