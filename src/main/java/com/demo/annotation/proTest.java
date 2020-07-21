package com.demo.annotation;

import com.demo.annotation.utils.PropertiesUtils;

/**
 * @Author: Psh
 * @Date: 2020/7/21 15:42
 */
public class proTest {
    public static void main(String[] args) {
        String value = PropertiesUtils.getValue("bootstrap.servers");
        System.out.println(value);
    }
}
