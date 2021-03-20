package com.wkk.jdk.thread.jmm;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class VolatileFeatureExample {
    volatile long v1 = 0L;

    public void set(long l) {
        v1 = l;
    }

    public void getAndIncrement() {
        v1++;
    }

    public long get() {
        return v1;
    }




}
