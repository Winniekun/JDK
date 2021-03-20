package com.wkk.jdk.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weikunkun
 * @since 2021/3/19
 */
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
    }
}
