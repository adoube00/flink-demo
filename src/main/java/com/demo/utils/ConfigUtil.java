package com.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class  ConfigUtil {


    public static Properties getKafkaConfig() throws IOException {
        InputStream stream = ConfigUtil.class.getResourceAsStream("/conf/conf.properties");
        Properties kafkaConfig = new Properties();
        kafkaConfig.load(stream);

        String topic = kafkaConfig.getProperty("topics");

        Properties pros = new Properties();
        pros.setProperty("topic", kafkaConfig.getProperty("topics"));
        pros.setProperty("bootstrap.servers", kafkaConfig.getProperty("bootstrap.servers"));
        pros.setProperty("group.id", kafkaConfig.getProperty("group.id"));
        pros.setProperty("enable.auto.commit", kafkaConfig.getProperty("enable.auto.commit"));
        return pros;
    }

}
