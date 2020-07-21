package com.demo.sink;

import com.demo.entity.Comsu;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 失败
 */
public class CsvSinkMysql {

    public static void main(String[] args) throws Exception {

//        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        //env.enableCheckpointing(500);
//        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));
//        //读取csv
//        DataSet<Comsu> coms = env.readCsvFile("C:/Users/psh71/Desktop/table.txt").pojoType(Comsu.class, "id","name");
//
//        coms.map(new MapFunction<Comsu, Comsu>() {
//            @Override
//            public Comsu map(Comsu value) throws Exception {
//                TimeUnit.SECONDS.sleep(5);
//                return value;
//            }
//        }).print();


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.enableCheckpointing(500);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));

        DataStreamSource<String> dateSource = env.readTextFile("C:/Users/psh71/Desktop/table.txt");
        dateSource.map(new MapFunction<String, Tuple2<String, Set<Comsu>>>() {
            @Override
            public Tuple2<String, Set<Comsu>> map(String s) throws Exception {
                String[] split = s.split("\t");
                String id = split[0];
                String name = split[1];
                Comsu comsu = new Comsu(id, name);
                Set<Comsu> set = new HashSet();
                set.add(comsu);
                return Tuple2.of(id,set);
            }
        });

        env.execute("test");

    }
}
