package com.demo.sink;


import com.alibaba.fastjson.JSON;
import com.demo.entity.Comsu;
import com.demo.sink.MySinkFunction.JDBCsink;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 成功
 * StreamExecutionEnvironment
 * DataStreamSource
 * 算子
 * addSink
 * RichSinkFunction
 */
public class Socket2Mysql {

    public static void main(String[] args) throws Exception {

        final Logger logger = LoggerFactory.getLogger(Socket2Mysql.class);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.enableCheckpointing(500);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));

        //socket端口
        DataStreamSource<String> streamSource = env.socketTextStream("175.24.130.58", 9999);
        SingleOutputStreamOperator<Comsu> map = streamSource.map(new ToComsu());
        map.print();
        map.addSink(new JDBCsink());
        env.execute("test");

    }

    private static class ToComsu extends RichMapFunction<String, Comsu> {
        private static final long serialVersionUID = 1180234853172462378L;
        @Override
        public Comsu map(String event) throws Exception {
//            String[] split = event.split(",");
//            String id = split[0];
//            String name = split[1];
            System.out.println(event);
            Comsu comsu = JSON.parseObject(event, Comsu.class);
            return comsu;
//            return new Comsu(id, name);
        }
        @Override
        public void open(Configuration parameters) throws Exception {
        }
    }

}
