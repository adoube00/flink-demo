package com.demo.sink;

import com.demo.entity.Comsu;
import com.demo.test.KafkaTest;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.io.InputStream;
import java.util.Properties;

/**
 * 未测试
 */
public class Kafka2Mysql {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.enableCheckpointing(500);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));

        InputStream stream = KafkaTest.class.getResourceAsStream("/conf/conf.properties");
        Properties kafkaConfig = new Properties();
        kafkaConfig.load(stream);

        String topic = kafkaConfig.getProperty("topics");

        Properties pros = new Properties();
        pros.setProperty("bootstrap.servers", kafkaConfig.getProperty("bootstrap.servers"));
        pros.setProperty("group.id", kafkaConfig.getProperty("group.id"));
        pros.setProperty("enable.auto.commit", kafkaConfig.getProperty("enable.auto.commit"));

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<String>(topic,
                new SimpleStringSchema(), pros);
        //写入偏移量
        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);

        SingleOutputStreamOperator<Comsu> map = env.addSource(kafkaConsumer).map(new ToComsu());
        map.print();
//        map.addSink(new JDBCsink());

//        dataStreamSource.addSink(new FlinkKafkaProducer<String>());

        // 同样效果
//        dataStreamSource.addSink(new PrintSinkFunction<>());

        env.execute("Flink add kafka data source");

    }


    private static class ToComsu extends RichMapFunction<String, Comsu> {
        @Override
        public Comsu map(String event) throws Exception {
            String[] split = event.split(",");
            String id = split[0];
            String name = split[1];
            return new Comsu(id, name);
        }
        @Override
        public void open(Configuration parameters) throws Exception {
        }
    }

}
