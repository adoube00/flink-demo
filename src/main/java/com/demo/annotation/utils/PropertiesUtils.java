package com.demo.annotation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取properties文件的值
 * @Author: Psh
 * @Date: 2020/7/21 15:37
 */
public class PropertiesUtils {
    /**
     * 获取properties文件的值
     *
     * @param key
     * @return value
     */
    public static String getValue(String key) {
        //配置文件路径
        String fileNamePath = "src/main/resources/conf/conf.properties";
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(fileNamePath);
            // prop.load(in); 直接这么写，如果properties文件中有中文汉字，则汉字会乱码。因为未设置编码格式。
            props.load(new InputStreamReader(in, "utf-8"));
            if (key == null || "".equals(key)) {
                throw new NullPointerException("key值为空");
            }
            String value = props.getProperty(key);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != in)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
