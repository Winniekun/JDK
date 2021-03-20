package com.wkk.jdk.thread.jmm;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class FinalExample {
    int i;
    final int j;
    static FinalExample obj;

    public FinalExample() {
        i = 1;
        j = 2;
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj;
        int a = object.i;
        int b = object.j;
    }
}
