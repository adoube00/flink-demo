package com.demo.function;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.util.Collector;

public class MyFunction extends ProcessWindowFunction {

    public MyFunction() {
        super();
    }
    //
    public void process(Object o, Context context, Iterable iterable, Collector collector) throws Exception {

    }



    @Override
    public void clear(Context context) throws Exception {
        super.clear(context);
    }
}
