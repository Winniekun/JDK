package com.wkk.jdk.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weikunkun
 * @since 2021/3/19
 */
@Slf4j
public class ThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                ThreadFactoryBuilder.builder().namePrefix("single-pool-verify-").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.submit(()-> {
                log.info("thread name : {}", Thread.currentThread().hashCode());
                if (System.currentTimeMillis() % 2 == 0) {
                    throw new RuntimeException("");
                }
            });
        }
        threadPoolExecutor.shutdown();
    }

    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
