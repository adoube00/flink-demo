package com.demo.function;

import org.apache.flink.api.common.functions.AggregateFunction;

public class AgFunction implements AggregateFunction {
    public Object createAccumulator() {
        return null;
    }

    public Object add(Object o, Object o2) {
        return null;
    }

    public Object getResult(Object o) {
        return null;
    }

    public Object merge(Object o, Object acc1) {
        return null;
    }
}
