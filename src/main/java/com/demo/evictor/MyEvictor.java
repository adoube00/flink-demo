package com.demo.evictor;

import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.windows.Window;

public class MyEvictor implements Evictor {
    /**
     * Optionally evicts elements. Called before windowing function.
     *
     * @param iterable The elements currently in the pane.
     * @param i The current number of elements in the pane.
     * @param window The {@link Window}
     * @param evictorContext The context for the Evictor
     */
    public void evictBefore(Iterable iterable, int i, Window window, EvictorContext evictorContext) {

    }
    /**
     * Optionally evicts elements. Called after windowing function.
     *
     * @param iterable The elements currently in the pane.
     * @param i The current number of elements in the pane.
     * @param window The {@link Window}
     * @param evictorContext The context for the Evictor
     */
    public void evictAfter(Iterable iterable, int i, Window window, EvictorContext evictorContext) {

    }
}
