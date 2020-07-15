package com.demo.test;

import org.apache.flink.api.common.accumulators.AverageAccumulator;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.io.InputStream;
import java.util.Properties;

public class AggWin {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.enableCheckpointing(500);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));

        //socket端口
//        DataStreamSource<String> socketSource = env.socketTextStream("175.24.130.58", 9999);

//        socketSource.flatMap(new RichFlatMapFunction<String, WordCount>() {
//            @Override
//            public void flatMap(String value, Collector<WordCount> collector) throws Exception {
//                String[] tokens = value.toLowerCase().split(",");
//                for (String token : tokens) {
//                    if (token.length() > 0) {
//                        collector.collect(new WordCount(token.trim(), 1));
//                    }
//                }
//            }
//        })
//                .keyBy(new KeySelector<WordCount, String>() {
//                    public String getKey(WordCount wc) throws Exception {
//                        return wc.word;
//                    }
//                }).timeWindow(Time.seconds(5))
//                .sum("count").print()
//                .setParallelism(1);
//
//
//        socketSource.print();
//        //读文件
//        DataStreamSource<String> sourceStream = env.readFile(new TextInputFormat("C:/Users/psh71/Desktop/test.txt"));
//
//
//        // stream 创建 timestamp assigner  和  watermark 机制
//        DataStream<Integer> withTimestampsAndWatermarks = sourceStream.assignTimestampsAndWatermarks(new MyTimestampsAndWatermarks());
//        DataStream<AverageAccumulator>  averageAccumulatorStream =  withTimestampsAndWatermarks
//                .keyBy(0)
//                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
//                .aggregate(new AverageAggregate());

        Path path = new Path("C:/Users/psh71/Desktop/test.txt");
        BasicTypeInfo typeInfo = BasicTypeInfo.INT_TYPE_INFO;
        //读文件
        DataStreamSource<String> sourceStream = env
                .readFile(new TextInputFormat(path), String.valueOf(path), FileProcessingMode.PROCESS_CONTINUOUSLY,1L);

        sourceStream.map(new String2Integer())
                    .timeWindowAll(Time.seconds(5))
                    .sum(0).print();




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


    public static class WordCount {
        private String word;
        private int count;

        public WordCount() {
        }

        public WordCount(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
