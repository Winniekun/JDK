package com.wkk.jdk.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weikunkun
 * @since 2021/3/30
 */
public class Print100 {
    private static int num = 0;
    private static AtomicInteger nums = new AtomicInteger(0);


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (nums.get() < 1000) {
                        System.out.println("thread name: " + Thread.currentThread().getName() + ", " + nums.incrementAndGet()
                        );
                    }
                }
            });
            thread.start();
        }
    }
}
