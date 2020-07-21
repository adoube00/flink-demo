package com.demo.sink.MySinkFunction;

import com.demo.entity.Comsu;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCsinkNorich implements SinkFunction<Comsu> {

    private Connection connection;
    private PreparedStatement preparedStatement;



    @Override
    public void invoke(Comsu value, Context context) throws Exception {
        // JDBC连接信息
        String USERNAME = "root";
        String PASSWORD = "171774";
        String DBURL = "jdbc:mysql://175.24.130.58:3306/sinkMysql";
        // 加载JDBC驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获取数据库连接
        connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        String sql = "insert into sinktest (id,name) values (?,?)";
        preparedStatement = connection.prepareStatement(sql);

        try {
            preparedStatement.setString(1,value.getId());
            preparedStatement.setString(2,value.getName());
            System.out.println("SQL-->"+preparedStatement.toString());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }

    }
}
