package com.demo.test;

import avro.shaded.com.google.common.cache.Cache;
import avro.shaded.com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CacheTe {
    public static void main(String[] args) {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(100, TimeUnit.MILLISECONDS)
                .build();
    }
}
