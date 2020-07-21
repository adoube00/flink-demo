package com.demo.annotation.utils;

import com.demo.annotation.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 *
 * @Author: Psh
 * @Date: 2020/7/21 15:45
 */
public class ValueUtils {
    /**
     *
     * @param obj
     * @throws Exception
     */
    public static void getValue(Object obj) throws Exception {
        Class<?> clz = obj.getClass();
        // 解析字段上是否有注解
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            boolean fieldHasAnno = field.isAnnotationPresent(Value.class);
            if (fieldHasAnno) {
                Value valueAnnoation = field.getAnnotation(Value.class);
                // 获取注解的值
                String value = valueAnnoation.value();
                // 通过注解设置的值获取properties的值
                String propertiedValue = Objects.requireNonNull(PropertiesUtils.getValue(value));
                // 获取属性的名字
                String name = field.getName();
                // 将属性的首字符大写， 构造get，set方法
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                // 获取属性的类型
                String type = field.getGenericType().toString();
                // 如果type是类类型，则前面包含"class "，后面跟类名
                // String 类型
                if (type.equals("class java.lang.String")) {
                    Method m = clz.getMethod("set" + name, String.class);
                    // invoke方法传递实例对象，因为要对实例处理，而不是类
                    m.invoke(obj, propertiedValue);
                }
                // int Integer类型
                if (type.equals("class java.lang.Integer")) {
                    Method m = clz.getMethod("set" + name, Integer.class);
                    m.invoke(obj, Integer.parseInt(propertiedValue));
                }
                if (type.equals("int")) {
                    Method m = clz.getMethod("set" + name, int.class);
                    m.invoke(obj, (int) Integer.parseInt(propertiedValue));
                }
                // boolean Boolean类型
                if (type.equals("class java.lang.Boolean")) {
                    Method m = clz.getMethod("set" + name, Boolean.class);
                    if (propertiedValue.equalsIgnoreCase("true")) {
                        m.invoke(obj, true);
                    }
                    if (propertiedValue.equalsIgnoreCase("false")) {
                        m.invoke(obj, true);
                    }
                }
                if (type.equals("boolean")) {
                    Method m = clz.getMethod("set" + name, boolean.class);
                    if (propertiedValue.equalsIgnoreCase("true")) {
                        m.invoke(obj, true);
                    }
                    if (propertiedValue.equalsIgnoreCase("false")) {
                        m.invoke(obj, true);
                    }
                }
                // long Long 数据类型
                if (type.equals("class java.lang.Long")) {
                    Method m = clz.getMethod("set" + name, Long.class);
                    m.invoke(obj, Long.parseLong(propertiedValue));
                }
                if (type.equals("long")) {
                    Method m = clz.getMethod("set" + name, long.class);
                    m.invoke(obj, Long.parseLong(propertiedValue));
                }
                // 时间数据类型
                if (type.equals("class java.util.Date")) {
                    Method m = clz.getMethod("set" + name, java.util.Date.class);
                    m.invoke(obj, DataConverter.convert(propertiedValue));
                }
            }
        }
    }

}
