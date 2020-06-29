//package com.demo.test;
//
//import org.apache.flink.api.common.functions.RichMapFunction;
//import org.apache.flink.api.common.restartstrategy.RestartStrategies;
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.runtime.jobgraph.JobVertex;
//import org.apache.flink.streaming.api.datastream.AllWindowedStream;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
//import org.apache.flink.streaming.api.windowing.time.Time;
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
//
//import java.io.InputStream;
//import java.util.Properties;
//
//public class KafkaTest {
//
//    public static void main(String[] args) throws Exception {
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
////        env.enableCheckpointing(500);
//        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));
//
//        InputStream stream = KafkaTest.class.getResourceAsStream("/conf/conf.properties");
//        Properties kafkaConfig = new Properties();
//        kafkaConfig.load(stream);
//
//        String topic = kafkaConfig.getProperty("topics");
//
//        Properties pros = new Properties();
//        pros.setProperty("bootstrap.servers", kafkaConfig.getProperty("bootstrap.servers"));
//        pros.setProperty("group.id", kafkaConfig.getProperty("group.id"));
//        pros.setProperty("enable.auto.commit", kafkaConfig.getProperty("enable.auto.commit"));
//
//        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<String>(topic,
//                new SimpleStringSchema(), pros);
//        //写入偏移量
//        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
//
//        DataStreamSource<String> dataStreamSource = env.addSource(kafkaConsumer);
//
//        dataStreamSource.print();
//        // 同样效果
////        dataStreamSource.addSink(new PrintSinkFunction<>());
//
//        env.execute("Flink add kafka data source");
//
//    }
//
//
//
//
//}
