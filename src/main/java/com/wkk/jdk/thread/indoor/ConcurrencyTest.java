package com.wkk.jdk.thread.indoor;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class ConcurrencyTest {
    private static final long count = 100001;

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            int a = 0;
            @Override
            public void run() {
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency: " + (time) + ",b = " + b);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis();
        System.out.println("serial: " + (time - start) + ",b = " + b + ", a = " + a);
    }

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }
}
