package com.demo.test;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.io.InputStream;
import java.util.Properties;

public class ProcessWF {
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
//        Properties pros = ConfigUtil.getKafkaConfig();
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<String>(topic,
                new SimpleStringSchema(), pros);
        //写入偏移量'
        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);

//        DataStreamSource dataStreamSource = env.addSource(kafkaConsumer);
        AllWindowedStream<Integer, TimeWindow> stream1 =env
                .addSource(kafkaConsumer)
                .map(new String2Integer())
                .timeWindowAll(Time.seconds(5));

        SingleOutputStreamOperator<Integer> sum = stream1.sum(0);
//        new ProcessWindowFunction<>();
        sum.print();


        env.execute("Flink add kafka data source");

    }

    private static class String2Integer extends RichMapFunction<String,Integer> {

        @Override
        public Integer map(String val) throws Exception {

            if(null==val){
                return 0;
            }

            return Integer.valueOf(val);
        }


    }
}
